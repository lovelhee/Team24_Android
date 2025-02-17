package com.challengeonair.challengeonairandroid.view.mypage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityMyPageBinding
import com.challengeonair.challengeonairandroid.view.home.HomeActivity
import com.challengeonair.challengeonairandroid.view.search.SearchActivity
import com.challengeonair.challengeonairandroid.viewmodel.MyPageViewModel
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
        val profileIntent = Intent(this, MyPageProfileActivity::class.java)
        challengeProfile.setOnClickListener {
            startActivity(profileIntent)
        }

        val challengeHistory = myPageBinding.glChallengeList
        val historyIntent = Intent(this, MyPageHistoryActivity::class.java)
        challengeHistory.setOnClickListener {
            startActivity(historyIntent)
        }

        val challengeSearch = myPageBinding.ibSearch
        val searchIntent = Intent(this, SearchActivity::class.java)
        challengeSearch.setOnClickListener {
            startActivity(searchIntent)
        }

        val challengeHome = myPageBinding.ibHome
        val homeIntent = Intent(this, HomeActivity::class.java)
        challengeHome.setOnClickListener {
            startActivity(homeIntent)
        }

    }
}
