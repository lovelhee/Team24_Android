package com.okaka.challengeonairandroid.view.challenge

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.viewmodel.CreateChallengeViewModel
import com.okaka.challengeonairandroid.databinding.FragmentCreateChallengeStep2Binding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

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

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Toast.makeText(context, "오류 발생: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
        }

        viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createChallengeViewModel.isDateSet.collect { isSet ->
                    binding.btnDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isSet) R.drawable.ic_check else 0, 0)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createChallengeViewModel.isStartTimeSet.collect { isSet ->
                    binding.btnStartTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isSet) R.drawable.ic_check else 0, 0)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createChallengeViewModel.isEndTimeSet.collect { isSet ->
                    binding.btnEndTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isSet) R.drawable.ic_check else 0, 0)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch(coroutineExceptionHandler) {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                createChallengeViewModel.isPointSet.collect { isSet ->
                    binding.btnSetChallengePoint.setCompoundDrawablesWithIntrinsicBounds(0, 0, if (isSet) R.drawable.ic_check else 0, 0)
                }
            }
        }

        binding.btnDate.setOnClickListener {
            toggleVisibility(binding.llDateLayout, binding.btnDate)
        }

        val initialYear = binding.dpDate.year
        val initialMonth = binding.dpDate.month + 1
        val initialDay = binding.dpDate.dayOfMonth
        createChallengeViewModel.updateChallengeDate("${initialYear}년 ${initialMonth}월 ${initialDay}일")

        binding.dpDate.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            createChallengeViewModel.updateChallengeDate("${year}년 ${monthOfYear + 1}월 ${dayOfMonth}일")
        }

        binding.btnDateDone.setOnClickListener {
            val date = binding.dpDate
            createChallengeViewModel.setDate(date.toString())
            binding.llDateLayout.visibility = View.GONE
        }

        binding.btnStartTime.setOnClickListener {
            toggleVisibility(binding.llStartTimeLayout, binding.btnStartTime)
        }

        val initialStartHour = binding.tpStartTime.hour
        val initialStartMinute = binding.tpStartTime.minute
        createChallengeViewModel.updateStartTime(String.format("%02d:%02d", initialStartHour, initialStartMinute))

        binding.tpStartTime.setOnTimeChangedListener { view, hour, minute ->
            createChallengeViewModel.updateStartTime(String.format("%02d:%02d", hour, minute))
        }

        binding.btnStartTimeDone.setOnClickListener {
            val startTime = binding.tpStartTime
            createChallengeViewModel.setStartTime(startTime.toString())
            binding.llStartTimeLayout.visibility = View.GONE
        }

        binding.btnEndTime.setOnClickListener {
            toggleVisibility(binding.llEndTimeLayout, binding.btnEndTime)
        }

        binding.tpEndTime.setOnTimeChangedListener { view, hour, minute ->
            createChallengeViewModel.updateEndTime(String.format("%02d:%02d", hour, minute))
        }

        val initialEndHour = binding.tpStartTime.hour
        val initialEndMinute = binding.tpStartTime.minute
        createChallengeViewModel.updateStartTime(String.format("%02d:%02d", initialEndHour, initialEndMinute))

        binding.btnEndTimeDone.setOnClickListener {
            val endTime = binding.tpEndTime
            createChallengeViewModel.setEndTime(endTime.toString())
            binding.llEndTimeLayout.visibility = View.GONE
        }

        binding.btnSetChallengePoint.setOnClickListener {
            toggleVisibility(binding.llSetChallengePoint, binding.btnSetChallengePoint)
        }

        binding.etSetChallengePoint.addTextChangedListener { text ->
            createChallengeViewModel.updatePoint(text.toString())
        }

        binding.btnSetPointDone.setOnClickListener {
            val point = binding.etSetChallengePoint.text.toString().toIntOrNull() ?: 0
            createChallengeViewModel.setPoint(point)
            binding.llSetChallengePoint.visibility = View.GONE
        }
    }

    private fun toggleVisibility(layout: View, button: View) {
        if (layout.visibility == View.VISIBLE) {
            button.setBackgroundResource(R.drawable.btn_create_challenge_uncompleted)
            layout.visibility = View.GONE
        }
        else {
            button.setBackgroundResource(R.drawable.btn_create_challenge_completed)
            layout.visibility = View.VISIBLE
        }
    }
}