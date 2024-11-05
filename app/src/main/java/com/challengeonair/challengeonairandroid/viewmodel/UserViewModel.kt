package com.example.challengeonairandroid.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeonairandroid.model.api.response.LogInResponse
import com.example.challengeonairandroid.model.api.response.LogoutResponse
import com.example.challengeonairandroid.model.api.response.ReIssueTokenResponse
import com.example.challengeonairandroid.model.api.response.UserDeletionResponse
import com.example.challengeonairandroid.model.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<LogInResponse>?>(null)
    val loginState = _loginState.asStateFlow()

    private val _logoutState = MutableStateFlow<Result<LogoutResponse>?>(null)
    val logoutState = _logoutState.asStateFlow()

    private val _deleteUserState = MutableStateFlow<Result<UserDeletionResponse>?>(null)
    val deleteUserState = _deleteUserState.asStateFlow()

    private val _reIssueTokenState = MutableStateFlow<Result<ReIssueTokenResponse>?>(null)
    val reIssueTokenState = _reIssueTokenState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            Log.d("UserViewModel", "로그인 시작")
            _loginState.value = userRepository.login().also { result ->
                Log.d("UserViewModel", "로그인 결과: $result")
            }
        }
    }

    fun logout(accessToken: String) {
        viewModelScope.launch {
            _logoutState.value = userRepository.logout(accessToken)
        }
    }

    fun deleteUser(accessToken: String) {
        viewModelScope.launch {
            _deleteUserState.value = userRepository.deleteUser(accessToken)
        }
    }

    fun reIssueToken(reIssueToken: String) {
        viewModelScope.launch {
            _reIssueTokenState.value = userRepository.reIssueToken(reIssueToken)
        }
    }

    // 상태 초기화 함수들
    fun resetLoginState() {
        _loginState.value = null
    }

    fun resetLogoutState() {
        _logoutState.value = null
    }

    fun resetDeleteUserState() {
        _deleteUserState.value = null
    }

    fun resetReIssueTokenState() {
        _reIssueTokenState.value = null
    }
}