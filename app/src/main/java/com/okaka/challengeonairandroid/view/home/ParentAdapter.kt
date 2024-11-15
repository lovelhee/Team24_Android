package com.okaka.challengeonairandroid.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.databinding.ItemHomeParentBinding
import com.okaka.challengeonairandroid.model.api.response.ChallengeResponse
import com.okaka.challengeonairandroid.model.data.entity.Category
import com.okaka.challengeonairandroid.model.data.entity.Challenge

class ParentAdapter(
    private var challenges: List<Challenge>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var selectedCategoryId: Int = Category.EXERCISE.id

    inner class ParentViewHolder(val binding: ItemHomeParentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(emptyList())

        init {
            binding.rvChallenge.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = childAdapter
            }

            updateCategoryUI(itemView.context)
            updateChallengeList()
        }

        fun bind() {
            setupCategoryClickListeners()
            updateEmptyMessageVisibility()
        }

        private fun setupCategoryClickListeners() {
            binding.layoutExercise.setOnClickListener {
                selectedCategoryId = Category.EXERCISE.id
                updateChallengeList()
                updateCategoryUI(itemView.context)
            }
            binding.layoutDevelop.setOnClickListener {
                selectedCategoryId = Category.DEVELOPMENT.id
                updateChallengeList()
                updateCategoryUI(itemView.context)
            }
            binding.layoutHobby.setOnClickListener {
                selectedCategoryId = Category.HOBBY.id
                updateChallengeList()
                updateCategoryUI(itemView.context)
            }
            binding.layoutStudy.setOnClickListener {
                selectedCategoryId = Category.STUDY.id
                updateChallengeList()
                updateCategoryUI(itemView.context)
            }
        }

        private fun updateEmptyMessageVisibility() {
            if (challenges.isEmpty()) {
                binding.rvChallenge.visibility = View.GONE
            } else {
                binding.rvChallenge.visibility = View.VISIBLE
            }
        }

        private fun updateChallengeList() {
            val filteredChallenges = challenges.filter { it.categoryId == selectedCategoryId }
            val challengeResponses = filteredChallenges.map { challenge ->
                ChallengeResponse(
                    challengeId = challenge.challengeId,
                    challengeName = challenge.challengeName,
                    challengeBody = challenge.challengeBody,
                    point = challenge.point,
                    challengeDate = challenge.challengeDate,
                    startTime = challenge.startTime,
                    endTime = challenge.endTime,
                    imageUrl = challenge.imageUrl,
                    minParticipantNum = challenge.minParticipantNum,
                    maxParticipantNum = challenge.maxParticipantNum,
                    currentParticipantNum = challenge.currentParticipantNum,
                    categoryId = challenge.categoryId,
                    hostId = challenge.hostId
                )
            }
            childAdapter.updateData(challengeResponses)
        }

        private fun updateCategoryUI(context: Context) {
            val defaultTextColor = ContextCompat.getColor(context, R.color.black)
            val selectedTextColor = ContextCompat.getColor(context, R.color.main_red)
            val defaultImageBackground = ContextCompat.getDrawable(context, android.R.color.transparent)
            val selectedImageBackground = ContextCompat.getDrawable(context, R.drawable.red_border)

            binding.ivExercise.background = if (selectedCategoryId == Category.EXERCISE.id) selectedImageBackground else defaultImageBackground
            binding.tvExercise.setTextColor(if (selectedCategoryId == Category.EXERCISE.id) selectedTextColor else defaultTextColor)

            binding.ivDevelop.background = if (selectedCategoryId == Category.DEVELOPMENT.id) selectedImageBackground else defaultImageBackground
            binding.tvDevelop.setTextColor(if (selectedCategoryId == Category.DEVELOPMENT.id) selectedTextColor else defaultTextColor)

            binding.ivHobby.background = if (selectedCategoryId == Category.HOBBY.id) selectedImageBackground else defaultImageBackground
            binding.tvHobby.setTextColor(if (selectedCategoryId == Category.HOBBY.id) selectedTextColor else defaultTextColor)

            binding.ivStudy.background = if (selectedCategoryId == Category.STUDY.id) selectedImageBackground else defaultImageBackground
            binding.tvStudy.setTextColor(if (selectedCategoryId == Category.STUDY.id) selectedTextColor else defaultTextColor)
        }
    }

    fun updateData(newChallenges: List<Challenge>) {
        challenges = newChallenges
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemHomeParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}