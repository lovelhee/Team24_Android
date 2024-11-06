package com.example.challengeonairandroid.view.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivityCreateChallengeBinding
import com.example.challengeonairandroid.viewmodel.CreateChallengeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

enum class TabKind {
    STEP1, STEP2
}

@AndroidEntryPoint
class CreateChallengeActivity : AppCompatActivity() {
    private val createChallengeViewModel: CreateChallengeViewModel by viewModels()
    private lateinit var createChallengeBinding: ActivityCreateChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChallengeBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_challenge)
        createChallengeBinding.lifecycleOwner = this

        val adapter = CreateChallengeStepAdapter(this)
        createChallengeBinding.viewPager.adapter = adapter

        TabLayoutMediator(createChallengeBinding.tlChallengeStep, createChallengeBinding.viewPager) { tab, position ->
            tab.text = when (TabKind.entries[position]) {
                TabKind.STEP1 -> getString(R.string.challenge_step1)
                TabKind.STEP2 -> getString(R.string.challenge_step2)
                else -> null
            }
        }.attach()

        createChallengeBinding.btnPrevious.setOnClickListener {
            if (createChallengeBinding.viewPager.currentItem > 0) {
                createChallengeBinding.viewPager.currentItem -= 1
            }
            else {
                finish()
            }
        }

        createChallengeBinding.btnNext.setOnClickListener {
            val currentItem = createChallengeBinding.viewPager.currentItem
            if (currentItem < adapter.itemCount - 1) {
                createChallengeBinding.viewPager.currentItem += 1
            }
            else {
                startActivity(CreateChallengeCompletedActivity.intent(this))
                finish()
            }
        }

        createChallengeBinding.ibClose.setOnClickListener {
            finish()
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, CreateChallengeActivity::class.java)
        }
    }
}