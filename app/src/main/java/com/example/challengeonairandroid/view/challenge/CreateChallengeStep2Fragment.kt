package com.example.challengeonairandroid.view.challenge

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.FragmentCreateChallengeStep2Binding
import com.example.challengeonairandroid.viewmodel.CreateChallengeViewModel

class CreateChallengeStep2Fragment : Fragment(R.layout.fragment_create_challenge_step2) {

    private val createChallengeViewModel: CreateChallengeViewModel by activityViewModels()
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

        binding.btnDate.setOnClickListener {
            toggleVisibility(binding.llDateLayout, binding.btnDate)
        }

        binding.btnStartTime.setOnClickListener {
            toggleVisibility(binding.llStartTimeLayout, binding.btnStartTime)
        }

        binding.btnEndTime.setOnClickListener {
            toggleVisibility(binding.llEndTimeLayout, binding.btnEndTime)
        }

        binding.btnSetChallengePoint.setOnClickListener {
            toggleVisibility(binding.llSetChallengePoint, binding.btnSetChallengePoint)
        }

        binding.dpDate.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            createChallengeViewModel.updateChallengeDate("$year-${monthOfYear + 1}-$dayOfMonth")
        }

        binding.tpStartTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            createChallengeViewModel.updateStartTime("$hourOfDay:$minute")
        }

        binding.tpEndTime.setOnTimeChangedListener { view, hourOfDay, minute ->
            createChallengeViewModel.updateEndTime("$hourOfDay:$minute")
        }

        binding.etSetChallengePoint.addTextChangedListener { text ->
            createChallengeViewModel.updatePoint(text.toString())
        }
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