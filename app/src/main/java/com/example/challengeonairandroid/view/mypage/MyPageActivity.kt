package com.example.challengeonairandroid.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.ActivityMyPageBinding
import com.example.challengeonairandroid.view.challenge.CreateChallengeCompletedActivity
import com.example.challengeonairandroid.view.home.HomeActivity
import com.example.challengeonairandroid.view.search.SearchActivity
import com.example.challengeonairandroid.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : AppCompatActivity() {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var waitingChallengeAdapter: WaitingChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myPageBinding: ActivityMyPageBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_page)
        myPageBinding.myPageData = myPageViewModel
        myPageBinding.lifecycleOwner = this

        val waitingChallengeRecyclerView = myPageBinding.rvWaitingChallengeList
        waitingChallengeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        waitingChallengeAdapter = WaitingChallengeAdapter(myPageViewModel.challenges.value)
        waitingChallengeRecyclerView.adapter = waitingChallengeAdapter

        val challengeProfile = myPageBinding.btnProfileChange
        challengeProfile.setOnClickListener {
            startActivity(MyPageProfileActivity.intent(this))
        }

        val challengeHistory = myPageBinding.glChallengeList
        challengeHistory.setOnClickListener {
            startActivity(MyPageHistoryActivity.intent(this))
        }

        val challengeSearch = myPageBinding.ibSearch
        challengeSearch.setOnClickListener {
            startActivity(SearchActivity.intent(this))
        }

        val challengeHome = myPageBinding.ibHome
        challengeHome.setOnClickListener {
            startActivity(HomeActivity.intent(this))
        }

    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MyPageActivity::class.java)
        }
    }
}
