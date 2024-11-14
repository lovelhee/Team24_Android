package com.okaka.challengeonairandroid.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ItemCategoryChallengeBinding
import com.okaka.challengeonairandroid.model.data.entity.Category
import com.okaka.challengeonairandroid.model.data.entity.Challenge

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
            binding.tvDate.text = challenge.challengeDate
            binding.tvStartTime.text = challenge.startTime
            binding.tvEndTime.text = challenge.endTime
            binding.tvCurrentNum.text = challenge.currentParticipantNum.toString()

            binding.tvCategory.text = getCategoryName(challenge.categoryId)

            Glide.with(binding.ivChallengeCover.context)
                .load(challenge.imageUrl)
                .fallback(R.drawable.sample_challenge_thumbnail)
                .into(binding.ivChallengeCover)

            updateParticipantIcons(challenge.currentParticipantNum, challenge.maxParticipantNum)
        }

        private fun updateParticipantIcons(currentNum: Int, maxNum: Int) {

            binding.iconContainer.removeViews(2, binding.iconContainer.childCount - 2)

            repeat(currentNum) {
                val currentIcon = ImageView(binding.iconContainer.context).apply {
                    setImageResource(R.drawable.ic_current_num)
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart = 4
                    }
                }
                binding.iconContainer.addView(currentIcon)
            }

            repeat(maxNum - currentNum) {
                val maxIcon = ImageView(binding.iconContainer.context).apply {
                    setImageResource(R.drawable.ic_max_num)
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        marginStart = 4
                    }
                }
                binding.iconContainer.addView(maxIcon)
            }
        }

        private fun getCategoryName(categoryId: Int): String {
            return when (categoryId) {
                Category.EXERCISE.id -> "운동"
                Category.DEVELOPMENT.id -> "자기계발"
                Category.HOBBY.id -> "취미"
                Category.STUDY.id -> "공부"
                else -> "알 수 없음"
            }
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