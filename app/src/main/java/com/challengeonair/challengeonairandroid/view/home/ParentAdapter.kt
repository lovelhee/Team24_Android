package com.challengeonair.challengeonairandroid.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challengeonair.challengeonairandroid.R
import com.challengeonair.challengeonairandroid.databinding.ItemHomeParentBinding
import com.challengeonair.challengeonairandroid.model.data.entity.Category
import com.challengeonair.challengeonairandroid.model.data.entity.Challenge

class ParentAdapter(
    private val categories: List<Category>,
    private val challengesByCategory: Map<Long, List<Challenge>>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    private var selectedCategoryId: Long = 1L

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

        fun bind(category: Category) {

//            // 카테고리 ID 임의 지정
//            binding.layoutExercise.setOnClickListener {
//                updateChallengeList(childAdapter, 1L)  // Exercise 카테고리 ID: 1
//            }
//            binding.layoutDevelop.setOnClickListener {
//                updateChallengeList(childAdapter, 2L)  // Self-development 카테고리 ID: 2
//            }
//            binding.layoutHobby.setOnClickListener {
//                updateChallengeList(childAdapter, 3L)  // Hobby 카테고리 ID: 3
//            }
//            binding.layoutStudy.setOnClickListener {
//                updateChallengeList(childAdapter, 4L)  // Study 카테고리 ID: 4
//            }
        }

        private fun setupCategoryClickListeners() {
            binding.layoutExercise.setOnClickListener {
                selectedCategoryId = 1L
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutDevelop.setOnClickListener {
                selectedCategoryId = 2L
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutHobby.setOnClickListener {
                selectedCategoryId = 3L
                updateChallengeList(childAdapter, selectedCategoryId)
                updateCategoryUI(itemView.context)
            }
            binding.layoutStudy.setOnClickListener {
                selectedCategoryId = 4L
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

            // 운동 카테고리 (기본 선택)
            binding.ivExercise.background = if (selectedCategoryId == 1L) selectedImageBackground else defaultImageBackground
            binding.tvExercise.setTextColor(if (selectedCategoryId == 1L) selectedTextColor else defaultTextColor)

            // 자기 계발 카테고리
            binding.ivDevelop.background = if (selectedCategoryId == 2L) selectedImageBackground else defaultImageBackground
            binding.tvDevelop.setTextColor(if (selectedCategoryId == 2L) selectedTextColor else defaultTextColor)

            // 취미 카테고리
            binding.ivHobby.background = if (selectedCategoryId == 3L) selectedImageBackground else defaultImageBackground
            binding.tvHobby.setTextColor(if (selectedCategoryId == 3L) selectedTextColor else defaultTextColor)

            // 공부 카테고리
            binding.ivStudy.background = if (selectedCategoryId == 4L) selectedImageBackground else defaultImageBackground
            binding.tvStudy.setTextColor(if (selectedCategoryId == 4L) selectedTextColor else defaultTextColor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = ItemHomeParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = 1
}