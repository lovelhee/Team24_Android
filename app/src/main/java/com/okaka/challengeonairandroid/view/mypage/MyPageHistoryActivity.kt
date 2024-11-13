package com.okaka.challengeonairandroid.view.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityMyPageHistoryBinding
import com.okaka.challengeonairandroid.model.data.dummyHistoryList
import com.okaka.challengeonairandroid.viewmodel.MyPageViewModel
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityMyPageHistoryBinding
import com.okaka.challengeonairandroid.model.data.entity.History
import com.okaka.challengeonairandroid.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageHistoryActivity : AppCompatActivity() {
    private val myPageViewModel: MyPageViewModel by viewModels()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myPageHistoryBinding: ActivityMyPageHistoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_page_history)
        myPageHistoryBinding.myPageData = myPageViewModel
        myPageHistoryBinding.lifecycleOwner = this

        val rvHistory = myPageHistoryBinding.rvHistory

        val btnBack = myPageHistoryBinding.btnBack
        btnBack.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
            finish()
        }

        historyAdapter = HistoryAdapter(dummyHistoryList)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = historyAdapter
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, MyPageHistoryActivity::class.java)
        }
    }
}