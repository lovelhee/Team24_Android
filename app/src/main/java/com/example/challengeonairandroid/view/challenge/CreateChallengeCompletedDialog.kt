package com.example.challengeonairandroid.view.challenge

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.DialogCreateChallengeCompletedBinding
import com.example.challengeonairandroid.viewmodel.CreateChallengeViewModel

class CreateChallengeCompletedDialog : DialogFragment() {

    private val createChallengeViewModel: CreateChallengeViewModel by activityViewModels()
    private var _binding: DialogCreateChallengeCompletedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_create_challenge_completed, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = createChallengeViewModel

        binding.btnHome.setOnClickListener {
            activity?.finish()
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}