package com.challengeonair.challengeonairandroid.view.challenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityParticipateChallengeBinding
import com.challengeonair.challengeonairandroid.databinding.DialogCancleBinding
import com.challengeonair.challengeonairandroid.databinding.DialogDeleteChallengeBinding
import com.challengeonair.challengeonairandroid.databinding.DialogReservationBinding
import com.challengeonair.challengeonairandroid.model.api.response.ChallengeResponse
import com.challengeonair.challengeonairandroid.model.api.response.UserProfileResponse
import com.challengeonair.challengeonairandroid.view.home.HomeActivity
import com.challengeonair.challengeonairandroid.viewmodel.ParticipateChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParticipateChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticipateChallengeBinding
    private val viewModel: ParticipateChallengeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_participate_challenge)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        var challengeId = -1L
        // 더미 데이터 설정
        val isTestingWithDummyData = true // 더미 데이터 사용 여부
        if (isTestingWithDummyData) {
            setDummyData()
        } else {
            // 실제 데이터 로드
            challengeId = intent.getLongExtra("challengeId", -1L)
            if (challengeId != -1L) {
                viewModel.loadChallengeDetails(challengeId)
            }
        }

        binding.btnDelete.setOnClickListener {
            showDeleteChallengeDialog()
        }

        binding.btnCancle.setOnClickListener {
            showCancleDialog()
        }

        binding.btnEnterChallenge.setOnClickListener {
            viewModel.joinChallenge(challengeId)
            binding.btnEnterChallenge.visibility = View.GONE
            binding.layoutUserEnterBtn.visibility = View.VISIBLE
            showReservationDialog()
        }

        binding.ibBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        observeViewModel()
    }

    private fun setDummyData() {

        viewModel.setChallengeData(
            challenge = ChallengeResponse(
                challengeId = 1L,
                challengeName = "달리기 챌린지",
                challengeBody = "우리 같이 달려용",
                point = 100,
                challengeDate = "2024-11-13",
                startTime = "10:00",
                endTime = "12:00",
                imageExtension = "https://images.pexels.com/photos/2526878/pexels-photo-2526878.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                minParticipantNum = 2,
                maxParticipantNum = 4,
                currentParticipantNum = 3,
                hostId = "host_dummy_id",
                categoryId = 1
            )
        )

        viewModel.setUserData(
            user = UserProfileResponse(
                userNickName = "정히공쥬",
                userBody = "공쥬입니당",
                imageUrl = "https://images.pexels.com/photos/2963409/pexels-photo-2963409.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                point = 200
            )
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.challenge.collectLatest { challenge ->
                        challenge?.let {
                            binding.tvTitle.text = it.challengeName
                            binding.tvDescription.text = it.challengeBody
                            binding.tvCurrentNum.text = it.currentParticipantNum.toString()
                            binding.tvMaxNum.text = it.maxParticipantNum.toString()
                            binding.tvDate.text = it.challengeDate
                            binding.tvStartTime.text = it.startTime
                            binding.tvEndTime.text = it.endTime
                            binding.tvPoint.text = it.point.toString()
                            Glide.with(this@ParticipateChallengeActivity)
                                .load(it.imageExtension)
                                .into(binding.ivChallengeCover)
                        }
                    }
                }

                launch {
                    viewModel.user.collectLatest { user ->
                        user?.let {
                            binding.tvHostName.text = it.userNickName
                            Glide.with(this@ParticipateChallengeActivity)
                                .load(it.imageUrl)
                                .into(binding.ivProfile)
                        }
                    }
                }
            }
        }
    }

    private fun showCancleDialog() {

        val dialogBinding = DataBindingUtil.inflate<DialogCancleBinding>(
            layoutInflater, R.layout.dialog_cancle, null, false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnCancle.setOnClickListener {
            binding.btnEnterChallenge.visibility = View.VISIBLE
            binding.layoutUserEnterBtn.visibility = View.GONE
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showReservationDialog() {
        val dialogBinding = DataBindingUtil.inflate<DialogReservationBinding>(
            layoutInflater, R.layout.dialog_reservation, null, false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnCheck.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteChallengeDialog() {
        val dialogBinding = DataBindingUtil.inflate<DialogDeleteChallengeBinding>(
            layoutInflater, R.layout.dialog_delete_challenge, null, false
        )

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnDelete.setOnClickListener {
            val intent = Intent(this, DeleteChallengeActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        dialog.show()
    }
}