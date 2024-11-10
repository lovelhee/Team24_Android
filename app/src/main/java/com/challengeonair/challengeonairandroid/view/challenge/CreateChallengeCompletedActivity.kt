package com.challengeonair.challengeonairandroid.view.challenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityCreateChallengeCompletedBinding
import com.challengeonair.challengeonairandroid.viewmodel.ChallengeCreateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChallengeCompletedActivity : AppCompatActivity() {
    private val createChallengeViewModel: ChallengeCreateViewModel by viewModels()
    private lateinit var createChallengeCompletedBinding: ActivityCreateChallengeCompletedBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChallengeCompletedBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge_completed)
        createChallengeCompletedBinding.lifecycleOwner = this

        createChallengeCompletedBinding.btnHome.setOnClickListener {
            finish()
        }

    }
}