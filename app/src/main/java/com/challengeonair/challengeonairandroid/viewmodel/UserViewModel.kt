package com.example.challengeonairandroid.viewmodel

import android.app.Activity
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeonairandroid.model.data.auth.TokenManager
import com.example.challengeonairandroid.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

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

    fun updateUiState(update: (UserUiState) -> UserUiState) {
        _uiState.update(update)
    }

    fun loginWithKakao(activity: Activity) {
        val webView = WebView(activity).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val cookieManager = CookieManager.getInstance()
                    val cookies = cookieManager.getCookie(url)

                    Log.d("UserViewModel", "Received cookies: $cookies")  // 토큰 확인용 로그

                    cookies?.split(";")?.forEach { cookie ->
                        val trimmedCookie = cookie.trim()
                        when {
                            trimmedCookie.startsWith("ACCESS_TOKEN=") -> {
                                val accessToken = trimmedCookie.substring("ACCESS_TOKEN=".length)
                                viewModelScope.launch {
                                    tokenManager.saveAccessToken(accessToken)
                                    updateUiState { it.copy(loginState = LoginState.LOGGED_IN) }
                                }
                                Log.d("UserViewModel", "Access Token saved to DataStore")  // 토큰 저장 확인 로그
                            }
                            trimmedCookie.startsWith("REFRESH_TOKEN=") -> {
                                val refreshToken = trimmedCookie.substring("REFRESH_TOKEN=".length)
                                viewModelScope.launch {
                                    tokenManager.saveRefreshToken(refreshToken)
                                }
                                Log.d("UserViewModel", "Refresh Token saved to DataStore")  // 토큰 저장 확인 로그
                            }
                        }
                    }
                }
            }
        }

        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(webView, true)
        }

        activity.setContentView(webView)
        webView.loadUrl("http://52.79.51.86:8080/oauth2/authorization/kakao")

    }

    suspend fun reissueToken() {
        updateUiState { it.copy(isLoading = true) }

        try {
            val reIssueToken = tokenManager.getReIssueToken()
            Log.d("UserViewModel", "현재 Refresh Token: $reIssueToken")

            if (reIssueToken.isNullOrEmpty()) {
                Log.d("UserViewModel", "Refresh Token이 없음, 토큰 만료 처리")
                updateUiState { currentState -> currentState.copy(loginState = LoginState.TOKEN_EXPIRED) }
                return
            }

            // TokenManager에서 가져온 refresh token을 쿠키 형식으로 포맷팅
            val cookieValue = "refreshToken=$reIssueToken"
            Log.d("UserViewModel", "쿠키 값: $cookieValue")

            userRepository.reIssueToken(cookieValue).fold(
                onSuccess = { response ->
                    Log.d("UserViewModel", "토큰 재발급 성공: Access Token: ${response.accessToken}, Refresh Token: ${response.reIssueToken}")
                    updateTokens(
                        accessToken = response.accessToken,
                        refreshToken = response.reIssueToken
                    )
                    updateUiState { it.copy(isLoading = false) }
                },
                onFailure = { exception ->
                    Log.e("UserViewModel", "토큰 재발급 실패", exception)
                    when {
                        isRefreshTokenExpired(exception) -> updateUiState { currentState -> currentState.copy(loginState = LoginState.TOKEN_EXPIRED,) }
                    }
                }
            )
        } catch (e: Exception) {
            Log.e("UserViewModel", "토큰 재발급 중 예외 발생", e)
        }
    }

    suspend fun updateTokens(accessToken: String, refreshToken: String) {
        Log.d("UserViewModel", "토큰 업데이트: Access Token: $accessToken, Refresh Token: $refreshToken")
        tokenManager.saveTokens(accessToken, refreshToken)
        updateUiState { currentState ->
            currentState.copy(
                loginState = LoginState.LOGGED_IN,
            )
        }
        Log.d("UserViewModel", "토큰 저장 완료")
    }

    fun logout() {
        Log.d("UserViewModel", "로그아웃 시작")
        viewModelScope.launch {
            tokenManager.clearTokens()
            updateUiState { currentState ->
                currentState.copy(
                    loginState = LoginState.NOT_LOGGED_IN
                )
            }
            Log.d("UserViewModel", "로그아웃 완료")
        }
    }

    private fun isUnauthorizedError(e: Exception): Boolean {
        return e is HttpException && e.code() == 401
    }

    private fun isRefreshTokenExpired(e: Throwable): Boolean {
        return e is HttpException && e.code() == 401 && e.response()?.errorBody()?.string()?.contains("refresh_token_expired") == true
    }

//    suspend fun <T> callWithToken(
//        apiCall: suspend (String) -> T
//    ): T {
//        try {
//            val currentToken = tokenManager.getAccessToken()
//            Log.d("UserViewModel", "API 호출 시도 (현재 토큰: $currentToken)")
//            return apiCall(currentToken)
//        } catch (e: Exception) {
//            when {
//                isUnauthorizedError(e) -> {
//                    Log.d("UserViewModel", "Access Token 만료, 재발급 시도")
//                    updateUiState { it.copy(loginState = LoginState.TOKEN_EXPIRED) }
//                    reissueToken()
//                    val newToken = _uiState.value.accessToken
//                    Log.d("UserViewModel", "새로운 토큰으로 API 재시도: $newToken")
//                    return apiCall(newToken)
//                }
//                isRefreshTokenExpired(e) -> {
//                    Log.d("UserViewModel", "Refresh Token 만료")
//                    handleAllTokensExpired()
//                    throw e
//                }
//                else -> {
//                    Log.e("UserViewModel", "API 호출 중 오류 발생", e)
//                    throw e
//                }
//            }
//        }
//    }
}