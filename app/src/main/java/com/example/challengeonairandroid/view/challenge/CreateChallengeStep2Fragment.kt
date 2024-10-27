package com.example.challengeonairandroid.view.challenge

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartTime.setOnClickListener {
            if (binding.llStartTimeLayout.visibility == View.VISIBLE) {
                binding.btnStartTime.setBackgroundResource(R.drawable.create_challenge_uncompleted)
                binding.llStartTimeLayout.visibility = View.GONE
            }
            else {
                binding.btnStartTime.setBackgroundResource(R.drawable.create_challenge_completed)
                binding.llStartTimeLayout.visibility = View.VISIBLE
            }
        }

        binding.btnEndTime.setOnClickListener {
            if (binding.llEndTimeLayout.visibility == View.VISIBLE) {
                binding.btnEndTime.setBackgroundResource(R.drawable.create_challenge_uncompleted)
                binding.llEndTimeLayout.visibility = View.GONE
            }
            else {
                binding.btnEndTime.setBackgroundResource(R.drawable.create_challenge_completed)
                binding.llEndTimeLayout.visibility = View.VISIBLE
            }
        }

        binding.btnSetChallengePoint.setOnClickListener {
            if (binding.llSetChallengePoint.visibility == View.VISIBLE) {
                binding.btnSetChallengePoint.setBackgroundResource(R.drawable.create_challenge_uncompleted)
                binding.llSetChallengePoint.visibility = View.GONE
            }
            else {
                binding.btnSetChallengePoint.setBackgroundResource(R.drawable.create_challenge_completed)
                binding.llSetChallengePoint.visibility = View.VISIBLE
            }
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
}