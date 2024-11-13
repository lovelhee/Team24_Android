package com.okaka.challengeonairandroid.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.okaka.challengeonairandroid.R
import com.okaka.challengeonairandroid.model.data.entity.Challenge
import com.okaka.challengeonairandroid.databinding.ItemHomeParentBinding
import com.okaka.challengeonairandroid.model.data.entity.Category

class ParentAdapter(
    private val challengesByCategory: Map<Long, List<Challenge>>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var selectedCategoryId: Long = Category.EXERCISE.id

    inner class ParentViewHolder(val binding: ItemHomeParentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(emptyList())

        init {
            binding.rvChallenge.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = childAdapter
            }

            updateChallengeList(childAdapter)
            setupCategoryClickListeners()
            updateCategoryUI(itemView.context)
        }

        private fun setupCategoryClickListeners() {
            binding.layoutExercise.setOnClickListener {
                selectedCategoryId = Category.EXERCISE.id
                updateChallengeList(childAdapter)
                updateCategoryUI(itemView.context)
            }
            binding.layoutDevelop.setOnClickListener {
                selectedCategoryId = Category.DEVELOPMENT.id
                updateChallengeList(childAdapter)
                updateCategoryUI(itemView.context)
            }
            binding.layoutHobby.setOnClickListener {
                selectedCategoryId = Category.HOBBY.id
                updateChallengeList(childAdapter)
                updateCategoryUI(itemView.context)
            }
            binding.layoutStudy.setOnClickListener {
                selectedCategoryId = Category.STUDY.id
                updateChallengeList(childAdapter)
                updateCategoryUI(itemView.context)
            }
        }

        private fun updateChallengeList(childAdapter: ChildAdapter, categoryId: Long = selectedCategoryId) {
            val challenges = challengesByCategory[categoryId] ?: emptyList()
            childAdapter.updateData(challenges)
        }

        private fun updateCategoryUI(context: Context) {
            val defaultTextColor = ContextCompat.getColor(context, R.color.black)
            val selectedTextColor = ContextCompat.getColor(context, R.color.main_red)
            val defaultImageBackground = ContextCompat.getDrawable(context, android.R.color.transparent)
            val selectedImageBackground = ContextCompat.getDrawable(context, R.drawable.red_border)

            when (selectedCategoryId) {
                Category.EXERCISE.id -> {
                    binding.ivExercise.background = selectedImageBackground
                    binding.tvExercise.setTextColor(selectedTextColor)
                }
                Category.DEVELOPMENT.id -> {
                    binding.ivDevelop.background = selectedImageBackground
                    binding.tvDevelop.setTextColor(selectedTextColor)
                }
                Category.HOBBY.id -> {
                    binding.ivHobby.background = selectedImageBackground
                    binding.tvHobby.setTextColor(selectedTextColor)
                }
                Category.STUDY.id -> {
                    binding.ivStudy.background = selectedImageBackground
                    binding.tvStudy.setTextColor(selectedTextColor)
                }
            }
            if (selectedCategoryId != Category.EXERCISE.id) {
                binding.ivExercise.background = defaultImageBackground
                binding.tvExercise.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != Category.DEVELOPMENT.id) {
                binding.ivDevelop.background = defaultImageBackground
                binding.tvDevelop.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != Category.HOBBY.id) {
                binding.ivHobby.background = defaultImageBackground
                binding.tvHobby.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != Category.STUDY.id) {
                binding.ivStudy.background = defaultImageBackground
                binding.tvStudy.setTextColor(defaultTextColor)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemHomeParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = 1
}