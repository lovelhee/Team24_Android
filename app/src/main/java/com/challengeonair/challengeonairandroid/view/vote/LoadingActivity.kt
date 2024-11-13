package com.challengeonair.challengeonairandroid.view.vote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityLoadingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_loading)
        binding.lifecycleOwner = this

        // TODO: 투표 결과 전송

        // TODO: 투표 결과 다이얼로그 띄우기

    }

    companion object {
        private const val VOTED_USER_UID = "voted_user_uid"

        fun intent(context: Context): Intent {
            return Intent(context, LoadingActivity::class.java)
        }

        fun intent(context: Context, userUid: Int? = null): Intent {
            return Intent(context, LoadingActivity::class.java).apply {
                userUid?.let {
                    putExtra(VOTED_USER_UID, it)
                }
            }
        }
    }
}