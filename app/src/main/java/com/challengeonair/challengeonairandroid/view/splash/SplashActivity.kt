package com.challengeonair.challengeonairandroid.view.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScreen.setKeepOnScreenCondition { true }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(HomeActivity.intent(this))
            finish()
        }, 2000) // 2초 후 HomeActivity로 전환
    }
}