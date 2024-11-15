package com.okaka.challengeonairandroid.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityLoginBinding
import com.okaka.challengeonairandroid.model.data.SharedPreferenceManager
import com.okaka.challengeonairandroid.view.home.HomeActivity
import com.okaka.challengeonairandroid.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()
    @Inject
    lateinit var sharedPreferenceManager: SharedPreferenceManager
    private lateinit var binding: ActivityLoginBinding

    companion object {
        private const val TAG = "LoginActivity"
        private const val KAKAO_LOGIN_REQUEST = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        Log.d(TAG, "onCreate")
        setupStateObserver()
        setupKakaoLoginButton()
    }


    private fun setupStateObserver() {
        lifecycleScope.launch {
            try {
                userViewModel.uiState.collect { state ->
                    Log.d(TAG, "State changed: ${state.loginState}")
                    when (state.loginState) {
                        is UserViewModel.LoginState.LOGGED_IN -> {
                            Log.d(TAG, "User logged in successfully")
                            // 프로필 설정 여부에 따라 분기
                            if (sharedPreferenceManager.getUserNickname() != null) {
                                startActivity(HomeActivity.intent(this@LoginActivity))
                            } else {
                                startActivity(SetProfileActivity.intent(this@LoginActivity))
                            }
                            finish()
                        }
                        is UserViewModel.LoginState.NOT_LOGGED_IN -> {
                            Log.d(TAG, "User not logged in")
                            // 로그인 필요
                        }
                        is UserViewModel.LoginState.TOKEN_EXPIRED -> {
                            Log.d(TAG, "Token expired, attempting reissue")
                            userViewModel.reissueToken()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in state observer", e)
                // 에러 처리
            }
        }
    }

    private fun setupKakaoLoginButton() {
        binding.kakaoLoginButton.setOnClickListener {
            Log.d(TAG, "Kakao login button clicked")
            startKakaoLogin()
        }
    }

    private fun startKakaoLogin() {
        val intent = Intent(this, KakaoWebViewActivity::class.java)
        startActivityForResult(intent, KAKAO_LOGIN_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == KAKAO_LOGIN_REQUEST) {
            when (resultCode) {
                RESULT_OK -> {
                    Log.d(TAG, "Kakao login successful")
                    // 필요한 경우 추가 처리
                }
                RESULT_CANCELED -> {
                    Log.d(TAG, "Kakao login cancelled or failed")
                    // 실패 처리
                }
            }
        }
    }
}