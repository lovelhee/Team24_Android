package com.okaka.challengeonairandroid.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.databinding.ActivityHomeBinding
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.data.auth.TokenManager
import com.okaka.challengeonairandroid.view.alarm.AlarmActivity
import com.okaka.challengeonairandroid.view.challenge.CreateChallengeActivity
import com.okaka.challengeonairandroid.view.mypage.MyPageActivity
import com.okaka.challengeonairandroid.view.search.SearchActivity
import com.okaka.challengeonairandroid.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "HomeActivity"

    @Inject
    lateinit var tokenManager: TokenManager

    private lateinit var parentAdapter: ParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        observeViewModel()

        homeViewModel.loadAllChallenges()
    }

    private fun setupUI() {
        // Button 클릭 리스너 설정
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

        // RecyclerView 설정
        parentAdapter = ParentAdapter(emptyList())
        binding.rvParent.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = parentAdapter
        }
    }

    private fun observeViewModel() {
        // challengesList의 업데이트를 수집하여 RecyclerView 업데이트
        lifecycleScope.launchWhenStarted {
            homeViewModel.challengesList.collect { challenges ->
                updateRecyclerView(challenges)
            }
        }

        // 로딩 상태를 수집하여 ProgressBar 표시
        lifecycleScope.launchWhenStarted {
            homeViewModel.isLoading.collect { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun updateRecyclerView(challenges: List<ChallengeResponse>) {
        // 어댑터의 데이터 업데이트 및 UI 갱신
        parentAdapter.updateData(challenges)
        Log.d(TAG, "Updated challengeList: $challenges")
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}