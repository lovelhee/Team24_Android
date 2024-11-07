package com.example.challengeonairandroid.view.challenge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivityCreateChallengeBinding
import com.example.challengeonairandroid.viewmodel.CreateChallengeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
            Log.d("createChallnege!!!!", "$currentItem")
            Log.d(":createChallnege!!!!", "${TabKind.STEP1.ordinal}")
            if (currentItem == TabKind.STEP1.ordinal) {
                if (createChallengeViewModel.isStep1Valid()) {
                    createChallengeBinding.viewPager.currentItem += 1
                }
                else {
                    Toast.makeText(this, "이미지를 제외한 모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    // TODO: 유효성 검사 문구 상황별 제공 추가
                }
            }
            else {
                if (createChallengeViewModel.isStep2Valid()) {
                    createChallengeViewModel.createChallenge()
                    createChallengeViewModel.sendChallengeDataToServer()
                }
                else {
                    Toast.makeText(this, "모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    // TODO: 유효성 검사 문구 상황별 제공 추가
                }
            }
        }

        lifecycleScope.launch {
            createChallengeViewModel.challengeCreationStatus.collectLatest { isCreated ->
                if (isCreated) {
                    val dialog = CreateChallengeCompletedDialog()
                    dialog.show(supportFragmentManager, "ChallengeCreatedDialog")
                    createChallengeViewModel.resetStatus()
                }
            }
        }

        createChallengeBinding.ibClose.setOnClickListener {
            finish()
        }
    }


    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, CreateChallengeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
    }
}