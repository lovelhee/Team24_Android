package com.okaka.challengeonairandroid.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.okaka.challengeonairandroid.view.home.HomeActivity
import com.okaka.challengeonairandroid.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KakaoWebViewActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var webView: WebView

    companion object {
        private const val TAG = "KakaoWebViewActivity"
        private const val KAKAO_AUTH_URL = "http://52.79.51.86:8080/oauth2/authorization/kakao"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Initializing KakaoWebViewActivity")

        initializeWebView()
        observeLoginState()
    }

    private fun initializeWebView() {
        webView = WebView(this).apply {
            settings.javaScriptEnabled = true

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d(TAG, "onPageFinished: URL: $url")

                    val cookieManager = CookieManager.getInstance()
                    val cookies = cookieManager.getCookie(url)

                    if (!cookies.isNullOrEmpty()) {
                        Log.d(TAG, "Cookies received, passing to ViewModel")
                        viewModel.processCookies(cookies)
                    }
                }
            }
        }

        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(webView, true)
        }

        setContentView(webView)
        webView.loadUrl(KAKAO_AUTH_URL)
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(TAG, "Login state changed: ${state.loginState}")
                    if (state.loginState is UserViewModel.LoginState.LOGGED_IN) {
                        Log.d(TAG, "Successfully logged in, navigating to HomeActivity")
                        navigateToHome()
                    }
                }
            }
        }
    }

    private fun navigateToHome() {
        Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
        }
        finish()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            setResult(RESULT_CANCELED)
            super.onBackPressed()
        }
    }
}