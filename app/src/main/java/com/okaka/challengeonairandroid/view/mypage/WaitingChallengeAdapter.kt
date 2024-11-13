package com.okaka.challengeonairandroid.view.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ItemWaitingChallengeBinding
import com.okaka.challengeonairandroid.model.data.entity.Challenge

class WaitingChallengeAdapter(
    private val waitingChallengeList: List<Challenge>,
) : RecyclerView.Adapter<WaitingChallengeAdapter.WaitingChallengeViewHolder>() {

    class WaitingChallengeViewHolder(val binding: ItemWaitingChallengeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitingChallengeViewHolder {
        val binding: ItemWaitingChallengeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_waiting_challenge,
            parent,
            false
        )
        return WaitingChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WaitingChallengeViewHolder, position: Int) {
        val currentChallenge = waitingChallengeList[position]
        holder.binding.challenge = currentChallenge

        Glide.with(holder.itemView.context)
            .load(currentChallenge.imageExtension)
            .placeholder(R.drawable.sample_challenge_thumbnail)
            .error(R.drawable.sample_challenge_thumbnail)
            .into(holder.binding.ivChallengeImg)
    }

    override fun getItemCount(): Int {
        return waitingChallengeList.size
    }
}