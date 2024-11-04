package com.example.challengeonairandroid.view.challenge

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.FragmentCreateChallengeStep2Binding

class CreateChallengeStep2Fragment : Fragment(R.layout.fragment_create_challenge_step2) {

    private lateinit var binding: FragmentCreateChallengeStep2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_challenge_step2, container, false
        )
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartTime.setOnClickListener {
            toggleVisibility(binding.llStartTimeLayout, binding.btnStartTime)
        }

        binding.btnEndTime.setOnClickListener {
            toggleVisibility(binding.llEndTimeLayout, binding.btnEndTime)
        }

        binding.btnSetChallengePoint.setOnClickListener {
            toggleVisibility(binding.llSetChallengePoint, binding.btnSetChallengePoint)
        }

        lateinit var startDate: String
        binding.dpStartTime.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            startDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            Log.d("createChallenge", "$startDate")
        }

        lateinit var startTime: String
        binding.tpStartTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            startTime = "$hourOfDay:$minute"
            Log.d("createChallenge", "$startTime")
        }

        lateinit var endDate: String
        binding.dpEndTime.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            endDate = "$year-${monthOfYear + 1}-$dayOfMonth"
            Log.d("createChallenge", "$endDate")
        }

        lateinit var endTime: String
        binding.tpEndTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            endTime = "$hourOfDay:$minute"
            Log.d("createChallenge", "$endTime")
        }

        lateinit var challengePoint: String
        binding.etSetChallengePoint.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                challengePoint = s.toString()
                Log.d("createChallenge", "${challengePoint}")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun toggleVisibility(layout: View, button: View) {
        if (layout.visibility == View.VISIBLE) {
            button.setBackgroundResource(R.drawable.create_challenge_uncompleted)
            layout.visibility = View.GONE
        }
        else {
            button.setBackgroundResource(R.drawable.create_challenge_completed)
            layout.visibility = View.VISIBLE
        }
    }
}