package com.okaka.challengeonairandroid.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.databinding.ActivityHomeBinding
import com.okaka.challengeonairandroid.model.data.auth.TokenManager
import com.okaka.challengeonairandroid.model.data.entity.Category
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.view.alarm.AlarmActivity
import com.okaka.challengeonairandroid.view.challenge.CreateChallengeActivity
import com.okaka.challengeonairandroid.view.mypage.MyPageActivity
import com.okaka.challengeonairandroid.view.search.SearchActivity
import com.okaka.challengeonairandroid.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "HomeActivity"
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnComplete.setOnClickListener {
            startActivity(CreateChallengeActivity.intent(this))
        }

        binding.ibSearch.setOnClickListener {
            startActivity(SearchActivity.intent(this))
        }

        binding.ibAlarm.setOnClickListener {
            startActivity(AlarmActivity.intent(this))
        }

        binding.ibMyPage.setOnClickListener {
            startActivity(MyPageActivity.intent(this))
        }

        lifecycleScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken()
                val refreshToken = tokenManager.getReIssueToken()
//                val allch = accessToken?.let { homeViewModel.loadAllChallenges(it) }
                Log.d(TAG, "Stored Access Token length: ${accessToken}")
                Log.d(TAG, "Stored Refresh Token length: ${refreshToken}")

                if (accessToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
                    Log.w(TAG, "One or both tokens are missing - Access: ${!accessToken.isNullOrEmpty()}, Refresh: ${!refreshToken.isNullOrEmpty()}")
                } else {
                    Log.d(TAG, "Both tokens are present and valid")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error checking tokens", e)
            }
        }

       
        val parentAdapter = ParentAdapter(challengesByCategory)
        binding.rvParent.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = parentAdapter
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}