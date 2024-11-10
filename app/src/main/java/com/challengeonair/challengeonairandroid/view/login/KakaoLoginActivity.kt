package com.challengeonair.challengeonairandroid.view.login

import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.challengeonair.challengeonairandroid.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class KakaoWebViewActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this).apply {
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val cookieManager = CookieManager.getInstance()
                    val cookies = cookieManager.getCookie(url)

                    Log.d("KakaoWebViewActivity", "Received cookies: $cookies")

                    cookies?.split(";")?.forEach { cookie ->
                        val trimmedCookie = cookie.trim()
                        when {
                            trimmedCookie.startsWith("ACCESS_TOKEN=") -> {
                                val accessToken = trimmedCookie.substring("ACCESS_TOKEN=".length)
                                runBlocking {
                                    viewModel.tokenManager.saveAccessToken(accessToken)
                                }
                                setResult(RESULT_OK)
                                finish() // 웹뷰 액티비티 종료
                            }
                            trimmedCookie.startsWith("REFRESH_TOKEN=") -> {
                                val refreshToken = trimmedCookie.substring("REFRESH_TOKEN=".length)
                                runBlocking {
                                    viewModel.tokenManager.saveRefreshToken(refreshToken)
                                }
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

        setContentView(webView)
        webView.loadUrl("http://52.79.51.86:8080/oauth2/authorization/kakao")
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