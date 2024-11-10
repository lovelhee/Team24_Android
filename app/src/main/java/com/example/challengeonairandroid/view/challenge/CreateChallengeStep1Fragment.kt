package com.example.challengeonairandroid.view.challenge

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.databinding.FragmentCreateChallengeStep1Binding

class CreateChallengeStep1Fragment : Fragment(R.layout.fragment_create_challenge_step1) {

    private lateinit var binding: FragmentCreateChallengeStep1Binding
    private lateinit var openGalleryLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lateinit var image: Intent
        openGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                image = result.data!!
                Log.d("createChallenge", "${image?.data}")
                // TODO: 이미지 저장 로직 추가
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
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_challenge_step1, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var categoryId: Int = -1
        binding.rgChallengeCategory.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbExercise -> categoryId = 0
                R.id.rbDevelopment -> categoryId = 1
                R.id.rbHobby -> categoryId = 2
                R.id.rbStudy -> categoryId = 3
            }
            Log.d("createChallenge", "${ categoryId }")
            // TODO: 카테고리 저장 로직 추가
        }


        lateinit var challengeTitle: String
        binding.etChallengeTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                challengeTitle = s.toString()
                Log.d("createChallenge", "${challengeTitle}")
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO: 챌린지 제목 저장 로직 추가
            }
        })

        lateinit var challengeBody: String
        binding.etChallengeBody.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                challengeBody = s.toString()
                Log.d("createChallenge", "${challengeBody}")
            }

            override fun afterTextChanged(s: Editable?) {
                // TODO: 챌린지 내용 저장 로직 추가
            }
        })

        var minNum = binding.tvMinNum.text.toString().toInt()
        var maxNum = binding.tvMaxNum.text.toString().toInt()
        binding.btnMinusMinNum.setOnClickListener {
            if (minNum > 2) {
                if (minNum - 1 <= maxNum) {
                    minNum -= 1
                    binding.tvMinNum.text = minNum.toString()
                }
                else {
                    Toast.makeText(requireContext(), "최소 인원은 최대 인원보다 작아야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "인원은 최소 2명입니다.", Toast.LENGTH_SHORT).show()
            }
            // TODO: 최소인원 저장 로직 추가
        }

        binding.btnPlusMinNum.setOnClickListener {
            if (minNum < 8) {
                if (minNum + 1 <= maxNum) {
                    minNum += 1
                    binding.tvMinNum.text = minNum.toString()
                }
                else {
                    Toast.makeText(requireContext(), "최소 인원은 최대 인원보다 작아야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "인원은 최대 8명입니다.", Toast.LENGTH_SHORT).show()
            }
            // TODO: 최소인원 저장 로직 추가
        }

        binding.btnMinusMaxNum.setOnClickListener {
            if (maxNum > 2) {
                if (maxNum - 1 >= minNum) {
                    maxNum -= 1
                    binding.tvMaxNum.text = maxNum.toString()
                }
                else {
                    Toast.makeText(requireContext(), "최대 인원은 최소 인원보다 커야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "인원은 최소 2명입니다.", Toast.LENGTH_SHORT).show()
            }
            // TODO: 최대인원 저장 로직 추가
        }

        binding.btnPlusMaxNum.setOnClickListener {
            if (maxNum < 8) {
                if (maxNum + 1 >= minNum) {
                    maxNum += 1
                    binding.tvMaxNum.text = maxNum.toString()
                }
                else {
                    Toast.makeText(requireContext(), "최대 인원은 최소 인원보다 커야 합니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "인원은 최대 8명입니다.", Toast.LENGTH_SHORT).show()
            }
            // TODO: 최대인원 저장 로직 추가
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