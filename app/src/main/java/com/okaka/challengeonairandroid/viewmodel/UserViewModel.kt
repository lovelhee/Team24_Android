package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okaka.challengeonairandroid.model.data.auth.TokenManager
import com.okaka.challengeonairandroid.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

private const val TAG = "UserViewModel"

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    val tokenManager: TokenManager
) : ViewModel() {
    data class UserUiState(
        val loginState: LoginState = LoginState.NOT_LOGGED_IN,
        val isLoading: Boolean = false,
    )

    sealed class LoginState {
        data object NOT_LOGGED_IN : LoginState()
        data object LOGGED_IN : LoginState()
        data object TOKEN_EXPIRED : LoginState()
    }

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "UserViewModel initialized")
    }

    fun updateUiState(update: (UserUiState) -> UserUiState) {
        val previousState = _uiState.value
        _uiState.update(update)
        val newState = _uiState.value
        Log.d(TAG, "UI State Updated - Previous: $previousState, New: $newState")
    }

    suspend fun reissueToken() {
        Log.d(TAG, "Starting token reissue process")
        updateUiState { it.copy(isLoading = true) }

        try {
            val reIssueToken = tokenManager.getReIssueToken()
            Log.d(TAG, "Current Refresh Token length: ${reIssueToken?.length ?: 0}")

            if (reIssueToken.isNullOrEmpty()) {
                Log.w(TAG, "Refresh Token is null or empty, updating state to TOKEN_EXPIRED")
                updateUiState { currentState -> currentState.copy(loginState = LoginState.TOKEN_EXPIRED) }
                return
            }

            val cookieValue = "refreshToken=$reIssueToken"
            Log.d(TAG, "Formatted cookie value length: ${cookieValue.length}")

            userRepository.reIssueToken(cookieValue).fold(
                onSuccess = { response ->
                    Log.d(TAG, "Token reissue successful - New Access Token length: ${response.accessToken.length}, New Refresh Token length: ${response.refreshToken.length}")
                    updateTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.refreshToken
                    )
                    updateUiState { it.copy(isLoading = false) }
                    Log.d(TAG, "Token reissue process completed successfully")
                },
                onFailure = { exception ->
                    Log.e(TAG, "Token reissue failed", exception)
                    when {
                        isRefreshTokenExpired(exception) -> {
                            Log.w(TAG, "Refresh token has expired")
                            updateUiState { currentState -> currentState.copy(loginState = LoginState.TOKEN_EXPIRED) }
                        }
                        else -> {
                            Log.e(TAG, "Unexpected error during token reissue", exception)
                        }
                    }
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "Exception during token reissue process", e)
            updateUiState { it.copy(isLoading = false) }
        }
    }

    fun processCookies(cookies: String) {
        Log.d(TAG, "Processing cookies")

        var accessToken: String? = null
        var refreshToken: String? = null

        // 먼저 쿠키에서 토큰들을 추출
        cookies.split(";").forEach { cookie ->
            val trimmedCookie = cookie.trim()
            when {
                trimmedCookie.startsWith("access_token=") -> {
                    accessToken = trimmedCookie.substring("ACCESS_TOKEN=".length)
                    Log.d(TAG, "Found ACCESS_TOKEN: $accessToken")
                }
                trimmedCookie.startsWith("refresh_token=") -> {
                    refreshToken = trimmedCookie.substring("REFRESH_TOKEN=".length)
                    Log.d(TAG, "Found REFRESH_TOKEN: $refreshToken")
                }
            }
        }

        // 두 토큰이 모두 있을 때만 저장 진행
        if (accessToken != null && refreshToken != null) {
            viewModelScope.launch {
                try {
                    Log.d(TAG, "Saving both tokens")
                    tokenManager.saveAccessToken(accessToken!!)
                    tokenManager.saveRefreshToken(refreshToken!!)
                    Log.d(TAG, "Both tokens saved successfully")

                    // 두 토큰이 모두 저장된 후에 상태 업데이트
                    updateUiState { it.copy(loginState = LoginState.LOGGED_IN) }
                    Log.d(TAG, "Login state updated to LOGGED_IN")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to save tokens", e)
                }
            }
        } else {
            Log.w(TAG, "Missing tokens - Access Token: ${accessToken != null}, Refresh Token: ${refreshToken != null}")
        }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        Log.d(TAG, "Updating tokens - Access Token length: ${accessToken.length}, Refresh Token length: ${refreshToken.length}")
        try {
            tokenManager.saveTokens(accessToken, refreshToken)
            Log.d(TAG, "Tokens successfully saved to TokenManager")
            updateUiState { currentState ->
                currentState.copy(loginState = LoginState.LOGGED_IN)
            }
            Log.d(TAG, "UI state updated to LOGGED_IN after token update")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update tokens", e)
            throw e
        }
    }

    fun logout() {
        Log.d(TAG, "Starting logout process")
        viewModelScope.launch {
            try {
                tokenManager.clearTokens()
                Log.d(TAG, "Tokens cleared successfully")
                updateUiState { currentState ->
                    currentState.copy(loginState = LoginState.NOT_LOGGED_IN)
                }
                Log.d(TAG, "Logout completed successfully")
            } catch (e: Exception) {
                Log.e(TAG, "Error during logout process", e)
            }
        }
    }

    private fun isUnauthorizedError(e: Exception): Boolean {
        val isUnauthorized = e is HttpException && e.code() == 401
        Log.d(TAG, "Checking unauthorized error: $isUnauthorized (${e.javaClass.simpleName}: ${e.message})")
        return isUnauthorized
    }

    private fun isRefreshTokenExpired(e: Throwable): Boolean {
        val isExpired = e is HttpException && e.code() == 401 &&
                e.response()?.errorBody()?.string()?.contains("refresh_token_expired") == true
        Log.d(TAG, "Checking refresh token expiration: $isExpired (${e.javaClass.simpleName}: ${e.message})")
        return isExpired
    }
}