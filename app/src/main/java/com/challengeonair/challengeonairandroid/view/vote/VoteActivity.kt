package com.challengeonair.challengeonairandroid.view.vote

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityVoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VoteActivity : AppCompatActivity() {

    private var selectedItemId: Int? = null
    private lateinit var binding: ActivityVoteBinding
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vote)
        binding.lifecycleOwner = this

        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.tvCount.text = "$secondsRemaining 초 남음"
            }

            override fun onFinish() {
                startActivity(LoadingActivity.intent(this@VoteActivity, selectedItemId))
                finish()
            }
        }

        countDownTimer.start()

        val gridItems = listOf(
            binding.llUser1,
            binding.llUser2,
            binding.llUser3,
            binding.llUser4
        )

        binding.btnSubmit.isEnabled = false

        for (item in gridItems) {
            item.setOnClickListener { onGridItemClick(item) }
        }

        binding.btnSubmit.setOnClickListener {
            Toast.makeText(this, "선택한 항목: ${selectedItemId}", Toast.LENGTH_SHORT).show()
            startActivity(LoadingActivity.intent(this@VoteActivity, selectedItemId))
            finish()
        }
    }

    private fun onGridItemClick(selectedView: View) {
        selectedItemId?.let { previousSelectedId ->
            val previousView = findViewById<View>(previousSelectedId)
            previousView.setBackgroundResource(R.drawable.card_background)
        }

        selectedItemId = selectedView.id
        selectedView.setBackgroundResource(R.drawable.card_selected_background)

        binding.btnSubmit.isEnabled = true
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, VoteActivity::class.java)
        }
    }
}