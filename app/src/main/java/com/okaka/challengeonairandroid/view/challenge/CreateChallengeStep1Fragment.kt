package com.okaka.challengeonairandroid.view.challenge

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.viewmodel.CreateChallengeViewModel
import com.okaka.challengeonairandroid.databinding.FragmentCreateChallengeStep1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateChallengeStep1Fragment : Fragment(R.layout.fragment_create_challenge_step1) {

    private val createChallengeViewModel: CreateChallengeViewModel by activityViewModels()
    private lateinit var binding: FragmentCreateChallengeStep1Binding
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                createChallengeViewModel.updateChallengeImage(result.data.toString())
            }
            else {
                Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_challenge_step1, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = createChallengeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rgChallengeCategory.setOnCheckedChangeListener { group, checkedId ->
            val selectedCategoryId = when (checkedId) {
                R.id.rbExercise -> 0
                R.id.rbDevelopment -> 1
                R.id.rbHobby -> 2
                R.id.rbStudy -> 3
                else -> -1
            }
            createChallengeViewModel.updateCategoryId(selectedCategoryId)
        }


        binding.etChallengeTitle.addTextChangedListener { text ->
            createChallengeViewModel.updateChallengeName(text.toString())
        }

        binding.etChallengeBody.addTextChangedListener { text ->
            createChallengeViewModel.updateChallengeBody(text.toString())
        }

        binding.btnMinusMinNum.setOnClickListener {
            var minNum = createChallengeViewModel.minParticipantNum.value
            var maxNum = createChallengeViewModel.maxParticipantNum.value
            if (minNum > 2) {
                if (minNum - 1 <= maxNum) {
                    minNum -= 1
                    createChallengeViewModel.updateMinParticipantNum(minNum)
                }
                else {
                    Toast.makeText(requireContext(), getString(R.string.error_minimum_people_less_than_maximum), Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.error_minimum_people_required), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPlusMinNum.setOnClickListener {
            var minNum = createChallengeViewModel.minParticipantNum.value
            var maxNum = createChallengeViewModel.maxParticipantNum.value
            if (minNum < 8) {
                if (minNum + 1 <= maxNum) {
                    minNum += 1
                    createChallengeViewModel.updateMinParticipantNum(minNum)
                }
                else {
                    Toast.makeText(requireContext(), getString(R.string.error_minimum_people_less_than_maximum), Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.error_maximum_people_allowed), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnMinusMaxNum.setOnClickListener {
            var minNum = createChallengeViewModel.minParticipantNum.value
            var maxNum = createChallengeViewModel.maxParticipantNum.value
            if (maxNum > 2) {
                if (maxNum - 1 >= minNum) {
                    maxNum -= 1
                    createChallengeViewModel.updateMaxParticipantNum(maxNum)
                }
                else {
                    Toast.makeText(requireContext(), getString(R.string.error_maximum_people_must_be_greater_than_minimum), Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.error_minimum_people_required), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPlusMaxNum.setOnClickListener {
            var minNum = createChallengeViewModel.minParticipantNum.value
            var maxNum = createChallengeViewModel.maxParticipantNum.value
            if (maxNum < 4) {
                if (maxNum + 1 >= minNum) {
                    maxNum += 1
                    createChallengeViewModel.updateMaxParticipantNum(maxNum)
                }
                else {
                    Toast.makeText(requireContext(), getString(R.string.error_maximum_people_must_be_greater_than_minimum), Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.error_maximum_people_allowed), Toast.LENGTH_SHORT).show()
            }
        }

        binding.ivChallengeImg.setOnClickListener {
            openGallery()
        }
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        openGalleryLauncher.launch(intent)
    }
}