package com.example.challengeonairandroid.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengeonairandroid.databinding.ItemCategoryChallengeBinding
import com.example.challengeonairandroid.model.data.Challenge

class SearchResultAdapter :
    ListAdapter<Challenge, SearchResultAdapter.ChallengeViewHolder>(ChallengeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemCategoryChallengeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChallengeViewHolder(private val binding: ItemCategoryChallengeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(challenge: Challenge) {
            binding.tvChallengeName.text = challenge.challengeName
//            binding.tvDate.text = challenge.challengeDate
//            binding.tvStartTime.text = challenge.startTime
//            binding.tvEndTime.text = challenge.endTime
//            binding.tvCurrentNum.text = challenge.currentParticipantNum.toString()
            Glide.with(binding.ivChallengeCover.context)
                .load(challenge.imageUrl)
                .into(binding.ivChallengeCover)

        }
    }

    class ChallengeDiffCallback : DiffUtil.ItemCallback<Challenge>() {
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem.challengeName == newItem.challengeName
        }

        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem == newItem
        }
    }
}