package com.okaka.challengeonairandroid.view.challenge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ActivityParticipateChallengeBinding
import com.okaka.challengeonairandroid.databinding.DialogDeleteChallengeBinding
import com.okaka.challengeonairandroid.databinding.DialogReservationBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.okaka.challengeonairandroid.databinding.DialogCancelBinding
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileResponse
import com.okaka.challengeonairandroid.model.api.response.UserProfileSpecificResponse
import com.okaka.challengeonairandroid.view.home.HomeActivity
import com.okaka.challengeonairandroid.viewmodel.ParticipateChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParticipateChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticipateChallengeBinding
    private val viewModel: ParticipateChallengeViewModel by viewModels()
    private var challengeId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_participate_challenge)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        challengeId = intent.getLongExtra("challengeId", -1L)

        if (challengeId != -1L) {
            viewModel.loadChallengeDetails(challengeId)
        }

        binding.btnDelete.setOnClickListener {
            showDeleteChallengeDialog()
        }

        binding.btnCancel.setOnClickListener {
            showCancelDialog()
        }

        binding.btnEnterChallenge.setOnClickListener {
            viewModel.joinChallenge(challengeId)
            binding.btnEnterChallenge.visibility = View.GONE
            binding.layoutUserEnterBtn.visibility = View.VISIBLE
            showReservationDialog(
                viewModel.challenge.value?.challengeDate ?: "",
                viewModel.challenge.value?.startTime ?: "",
                viewModel.challenge.value?.endTime ?: ""
            )
        }

        binding.ibBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                        if (it.currentParticipantNum >= it.maxParticipantNum) {
                            binding.tvRecruit.visibility = View.INVISIBLE
                            binding.tvRecruitComplete.visibility = View.VISIBLE
                            binding.btnEnterChallenge.apply {
                                isEnabled = false
                                text = "방이 가득 찼어요"
                                setTextColor(ContextCompat.getColor(context, R.color.btn_text_gray))
                                setBackgroundColor(ContextCompat.getColor(context, R.color.btn_background_gray))
                            }
                        } else {
                            binding.tvRecruit.visibility = View.VISIBLE
                            binding.tvRecruitComplete.visibility = View.INVISIBLE
                            binding.btnEnterChallenge.apply {
                                isEnabled = true
                                text = "참여하기"
                                setBackgroundColor(ContextCompat.getColor(context, R.color.main_red))
                            }
                        }
                    }
                }
                viewModel.specificUser.collectLatest { specificUser ->
                    specificUser?.let {
                        binding.tvHostName.text = it.userNickName
                        Glide.with(this@ParticipateChallengeActivity)
                            .load(it.imageUrl)
                            .into(binding.ivProfile)
                        Log.d("ParticipateChallengeActivity", "Host Info Displayed: ${it.userNickName}")
                    } ?: Log.d("ParticipateChallengeActivity", "Host Info is null")
                }

                viewModel.isHost.collectLatest { isHost ->
                    if (isHost) {
                        binding.layoutHostBtn.visibility = View.VISIBLE
                        binding.layoutUserBtn.visibility = View.GONE
                    } else {
                        binding.layoutHostBtn.visibility = View.GONE
                        binding.layoutUserBtn.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun showCancelDialog() {

        val dialogBinding = DialogCancelBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnCancel.setOnClickListener {
            viewModel.cancelChallenge(challengeId)
            dialog.dismiss()
            binding.btnEnterChallenge.visibility = View.VISIBLE
            binding.layoutUserEnterBtn.visibility = View.GONE
        }

        dialog.show()
    }

    private fun showReservationDialog(challengeDate: String, startTime: String, endTime: String) {
        val dialogBinding = DialogReservationBinding.inflate(layoutInflater)
        dialogBinding.tvReservationDate.text = challengeDate
        dialogBinding.tvReservationStartTime.text = startTime
        dialogBinding.tvReservationEndTime.text = endTime

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
        val dialogBinding = DialogDeleteChallengeBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.btnBack.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnDelete.setOnClickListener {
            viewModel.deleteChallenge(challengeId)
            val intent = Intent(this, DeleteChallengeActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
            finish()
        }

        dialog.show()
    }
}