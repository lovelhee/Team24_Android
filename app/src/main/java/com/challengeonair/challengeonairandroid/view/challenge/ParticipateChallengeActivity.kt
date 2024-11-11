package com.challengeonair.challengeonairandroid.view.challenge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ActivityParticipateChallengeBinding
import com.challengeonair.challengeonairandroid.databinding.DialogCancleBinding
import com.challengeonair.challengeonairandroid.databinding.DialogDeleteChallengeBinding
import com.challengeonair.challengeonairandroid.databinding.DialogReservationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipateChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityParticipateChallengeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_participate_challenge)

        binding.btnDelete.setOnClickListener {
            showDeleteChallengeDialog()
        }

        binding.btnCancle.setOnClickListener {
            showCancleDialog()
        }

        binding.btnEnterChallenge.setOnClickListener {
            binding.btnEnterChallenge.visibility = View.GONE
            binding.layoutUserEnterBtn.visibility = View.VISIBLE

            showReservationDialog()
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