package com.okaka.challengeonairandroid.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GoogleLogInViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val TAG = "GoogleLogInViewModel"
    }

    private val _isLoading = MutableLiveData<Int>()
    val isLoading: LiveData<Int> = _isLoading

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    sealed class LoginState {
        object Loading : LoginState()
        data class Success(val email: String, val token: String) : LoginState()
        data class Error(val message: String) : LoginState()
    }

    fun setLoading(loading: Boolean) {
        _isLoading.value = if (loading) View.VISIBLE else View.GONE
        if (loading) {
            _loginState.value = LoginState.Loading
        }
    }

    fun handleSignInSuccess(account: GoogleSignInAccount) {
        Log.d(TAG, "Handling sign in success")
        setLoading(false)

        // Here you would typically send the ID token to your backend
        account.idToken?.let { token ->
            Log.d(TAG, "ID Token received: $token")
            _loginState.value = LoginState.Success(
                email = account.email ?: "",
                token = token
            )

            // TODO: Send token to backend
            // val tokenValidationResult = backendApi.validateToken(token)
            // Log.d(TAG, "Backend validation result: $tokenValidationResult")
        } ?: run {
            Log.e(TAG, "No ID token received")
            handleSignInError("No ID token received")
        }
    }

    fun handleSignInError(errorMessage: String) {
        Log.e(TAG, "Sign in error: $errorMessage")
        setLoading(false)
        _loginState.value = LoginState.Error(errorMessage)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}