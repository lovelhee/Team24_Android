package com.example.challengeonairandroid.view.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivityLoginBinding
import com.example.challengeonairandroid.viewmodel.UserViewModel
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupStateObserver()
        setupKakaoLoginButton()
    }

    private fun setupStateObserver() {
        lifecycleScope.launch {
            try {
                userViewModel.uiState.collect { state ->
                    when (state.loginState) {
                        is UserViewModel.LoginState.LOGGED_IN -> {
                            // 로그인 성공 처리
                        }
                        is UserViewModel.LoginState.NOT_LOGGED_IN -> {
                            // 로그인 필요
                        }
                        is UserViewModel.LoginState.TOKEN_EXPIRED -> {
                            userViewModel.reissueToken()
                        }
                    }
                }
            } catch (e: Exception) {
                // 에러 처리
            }
        }
    }

    private fun setupKakaoLoginButton() {
        binding.kakaoLoginButton.setOnClickListener {
            userViewModel.loginWithKakao(this@LoginActivity)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}