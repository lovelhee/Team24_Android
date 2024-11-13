package com.okaka.challengeonairandroid.view.challenge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityDeleteChallengeBinding
import com.okaka.challengeonairandroid.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_delete_challenge)

        binding.imgBtnBack.setOnClickListener {
            goToHome()
        }

        binding.btnHome.setOnClickListener {
            goToHome()
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}