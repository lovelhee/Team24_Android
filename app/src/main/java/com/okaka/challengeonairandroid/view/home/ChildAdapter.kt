package com.okaka.challengeonairandroid.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.model.data.entity.Category
import com.okaka.challengeonairandroid.databinding.ItemCategoryChallengeBinding

class ChildAdapter(
    private var challenges: List<Challenge>,
    private val categories: List<Category>
) : RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemCategoryChallengeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: Challenge) {
            binding.tvChallengeName.text = challenge.challengeName
            binding.tvDate.text = challenge.challengeDate
            binding.tvStartTime.text = challenge.startTime
            binding.tvEndTime.text = challenge.endTime
            binding.tvCurrentNum.text = challenge.currentParticipantNum.toString()

            binding.tvCategory.text = getCategoryName(challenge.categoryId)

            Glide.with(binding.ivChallengeCover.context)
                .load(challenge.imageExtension)
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
            return categories.first { it.categoryId == categoryId.toLong() }.categoryName
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