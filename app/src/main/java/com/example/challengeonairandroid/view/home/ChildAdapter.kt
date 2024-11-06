package com.example.challengeonairandroid.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengeonairandroid.R
import com.example.challengeonairandroid.model.data.Challenge
import com.example.challengeonairandroid.databinding.ItemCategoryChallengeBinding

class ChildAdapter(
    private var challenges: List<Challenge>
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemCategoryChallengeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: Challenge) {
            binding.tvChallengeName.text = challenge.challengeName
            binding.tvDate.text = challenge.challengeDate
            binding.tvStartTime.text = challenge.startTime
            binding.tvEndTime.text = challenge.endTime
            binding.tvCurrentNum.text = challenge.currentParticipantNum.toString()

            Glide.with(binding.ivChallengeCover.context)
                .load(challenge.imageUrl)
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
                1 -> "운동"
                2 -> "자기 계발"
                3 -> "취미"
                4 -> "공부"
                else -> "기타"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemCategoryChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(challenges[position])
    }

    override fun getItemCount(): Int = challenges.size

    fun updateData(newChallenges: List<Challenge>) {
        challenges = newChallenges
        notifyDataSetChanged()
    }
}