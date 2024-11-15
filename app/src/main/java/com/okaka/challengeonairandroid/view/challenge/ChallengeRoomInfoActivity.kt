package com.okaka.challengeonairandroid.view.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.okaka.challengeonairandroid.viewmodel.ChallengeDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengeRoomInfoActivity : AppCompatActivity() {
    private val challengeRoomInfoViewModel: ChallengeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewModel 사용
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, ChallengeRoomInfoActivity::class.java)
        }
    }
}