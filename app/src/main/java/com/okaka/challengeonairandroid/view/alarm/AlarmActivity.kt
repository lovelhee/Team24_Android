package com.okaka.challengeonairandroid.view.alarm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityAlarmBinding
import com.okaka.challengeonairandroid.model.data.entity.Alarm
import com.okaka.challengeonairandroid.view.mypage.AlarmAdapter

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var alarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dummyAlarms = listOf(
            Alarm(id = 1, message = "Wake up!", isConfirmed = false),
            Alarm(id = 2, message = "Meeting at 10 AM", isConfirmed = true),
            Alarm(id = 3, message = "Lunch with friends", isConfirmed = false),
            Alarm(id = 4, message = "Workout session", isConfirmed = true)
        )

        alarmAdapter = AlarmAdapter(dummyAlarms)

        val alarmBinding: ActivityAlarmBinding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)
        alarmBinding.lifecycleOwner = this

        val alarmRecyclerView = alarmBinding.rvAlarm
        alarmRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        alarmRecyclerView.adapter = alarmAdapter

        alarmBinding.btnBack.setOnClickListener {
            finish()
        }

    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, AlarmActivity::class.java)
        }
    }
}