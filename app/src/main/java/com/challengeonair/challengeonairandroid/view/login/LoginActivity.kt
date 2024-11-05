package com.example.challengeonairandroid.view.login

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabsIntent
import com.example.challengeonairandroid.viewmodel.UserViewModel


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // CookieManager 설정
        CookieManager.getInstance().apply {
            setAcceptCookie(true)
            setAcceptThirdPartyCookies(WebView(this@LoginActivity), true)
        }

        val url = "http://52.79.130.181:8080/oauth2/authorization/naver"
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(url))

        // 쿠키 확인
        checkCookies()
    }

    private fun checkCookies() {
        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie("http://52.79.130.181:8080")

        Log.d("LoginActivity", "All cookies: $cookies")

        cookies?.split(";")?.forEach { cookie ->
            val trimmedCookie = cookie.trim()
            when {
                trimmedCookie.startsWith("access_token=") -> {
                    val accessToken = trimmedCookie.substring("access_token=".length)
                    Log.d("LoginActivity", "Access Token: $accessToken")
                }
                trimmedCookie.startsWith("refresh_token=") -> {
                    val refreshToken = trimmedCookie.substring("refresh_token=".length)
                    Log.d("LoginActivity", "Refresh Token: $refreshToken")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 앱으로 돌아올 때마다 쿠키 확인
        checkCookies()
    }
}