package com.challengeonair.challengeonairandroid.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.model.data.entity.Category
import com.challengeonair.challengeonairandroid.model.data.entity.Challenge
import com.challengeonair.challengeonairandroid.databinding.ItemHomeParentBinding

enum class CategoryType(val id: Long) {
    EXERCISE(1L),
    DEVELOPMENT(2L),
    HOBBY(3L),
    STUDY(4L)
}

class ParentAdapter(
    private val categories: List<Category>,
    private val challengesByCategory: Map<Long, List<Challenge>>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var selectedCategoryId: Long = CategoryType.EXERCISE.id

    inner class ParentViewHolder(val binding: ItemHomeParentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(emptyList(), categories)

        init {
            binding.rvChallenge.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = childAdapter
            }

            updateChallengeList(childAdapter, 1L)
            setupCategoryClickListeners()
            updateCategoryUI(itemView.context)
        }

        private fun setupCategoryClickListeners() {
            binding.layoutExercise.setOnClickListener {
                selectedCategoryId = CategoryType.EXERCISE.id
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutDevelop.setOnClickListener {
                selectedCategoryId = CategoryType.DEVELOPMENT.id
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutHobby.setOnClickListener {
                selectedCategoryId = CategoryType.HOBBY.id
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutStudy.setOnClickListener {
                selectedCategoryId = CategoryType.STUDY.id
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
        }

        private fun updateChallengeList(childAdapter: ChildAdapter, categoryId: Long) {
            val challenges = challengesByCategory[categoryId] ?: emptyList()
            childAdapter.updateData(challenges)
        }

        private fun updateCategoryUI(context: Context) {
            val defaultTextColor = ContextCompat.getColor(context, R.color.black)
            val selectedTextColor = ContextCompat.getColor(context, R.color.main_red)
            val defaultImageBackground = ContextCompat.getDrawable(context, android.R.color.transparent)
            val selectedImageBackground = ContextCompat.getDrawable(context, R.drawable.red_border)

            when (selectedCategoryId) {
                CategoryType.EXERCISE.id -> {
                    binding.ivExercise.background = selectedImageBackground
                    binding.tvExercise.setTextColor(selectedTextColor)
                }
                CategoryType.DEVELOPMENT.id -> {
                    binding.ivDevelop.background = selectedImageBackground
                    binding.tvDevelop.setTextColor(selectedTextColor)
                }
                CategoryType.HOBBY.id -> {
                    binding.ivHobby.background = selectedImageBackground
                    binding.tvHobby.setTextColor(selectedTextColor)
                }
                CategoryType.STUDY.id -> {
                    binding.ivStudy.background = selectedImageBackground
                    binding.tvStudy.setTextColor(selectedTextColor)
                }
            }
            if (selectedCategoryId != CategoryType.EXERCISE.id) {
                binding.ivExercise.background = defaultImageBackground
                binding.tvExercise.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != CategoryType.DEVELOPMENT.id) {
                binding.ivDevelop.background = defaultImageBackground
                binding.tvDevelop.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != CategoryType.HOBBY.id) {
                binding.ivHobby.background = defaultImageBackground
                binding.tvHobby.setTextColor(defaultTextColor)
            }
            if (selectedCategoryId != CategoryType.STUDY.id) {
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