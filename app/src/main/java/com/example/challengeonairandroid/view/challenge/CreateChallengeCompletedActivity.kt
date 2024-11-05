package com.example.challengeonairandroid.view.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivityCreateChallengeBinding
import com.example.challengeonairandroid.databinding.ActivityCreateChallengeCompletedBinding
import com.example.challengeonairandroid.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChallengeCompletedActivity : AppCompatActivity() {
    private val createChallengeViewModel: ChallengeViewModel by viewModels()
    private lateinit var createChallengeCompletedBinding: ActivityCreateChallengeCompletedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChallengeCompletedBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge_completed)
        createChallengeCompletedBinding.lifecycleOwner = this

        createChallengeCompletedBinding.btnHome.setOnClickListener {
            finish()
        }

    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, CreateChallengeCompletedActivity::class.java)
        }
    }
}