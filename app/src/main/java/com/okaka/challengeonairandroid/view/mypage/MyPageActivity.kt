package com.okaka.challengeonairandroid.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.view.home.HomeActivity
import com.okaka.challengeonairandroid.viewmodel.MyPageViewModel
import com.okaka.challengeonairandroid.databinding.ActivityMyPageBinding
import com.okaka.challengeonairandroid.view.alarm.AlarmActivity
import com.okaka.challengeonairandroid.view.search.SearchFragment
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SearchFragment()) // fragmentContainer에 SearchFragment 추가
                .addToBackStack(null) // 뒤로 가기 스택에 추가
                .commit()
        }

        val challengeHome = myPageBinding.ibHome
        challengeHome.setOnClickListener {
            startActivity(HomeActivity.intent(this))
        }

        myPageBinding.ibAlarm.setOnClickListener {
            startActivity(AlarmActivity.intent(this))
        }

    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MyPageActivity::class.java)
        }
    }
}
