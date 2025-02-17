package com.challengeonair.challengeonairandroid.view.challenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityCreateChallengeBinding
import com.challengeonair.challengeonairandroid.viewmodel.ChallengeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChallengeActivity : AppCompatActivity() {
    private val createChallengeViewModel: ChallengeViewModel by viewModels()
    private lateinit var createChallengeBinding: ActivityCreateChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChallengeBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge)
        createChallengeBinding.lifecycleOwner = this

        val adapter = CreateChallengeStepAdapter(this)
        createChallengeBinding.viewPager.adapter = adapter

        TabLayoutMediator(createChallengeBinding.tlChallengeStep, createChallengeBinding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.challenge_step1)
                1 -> getString(R.string.challenge_step2)
                else -> null
            }
        }.attach()

        createChallengeBinding.btnPrevious.setOnClickListener {
            if (createChallengeBinding.viewPager.currentItem > 0) {
                createChallengeBinding.viewPager.currentItem -= 1
            }
        }

        createChallengeBinding.btnNext.setOnClickListener {
            val currentItem = createChallengeBinding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                createChallengeBinding.viewPager.currentItem += 1
            }
            else {
                val intent = Intent(this, CreateChallengeCompletedActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        createChallengeBinding.ibClose.setOnClickListener {
            finish()
        }
    }
}