package com.example.challengeonairandroid.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeonairandroid.databinding.ItemHomeParentBinding
import com.example.challengeonairandroid.model.data.Category
import com.example.challengeonairandroid.model.data.Challenge

class ParentAdapter(
    private val categories: List<Category>,
    private val challengesByCategory: Map<Long, List<Challenge>>
) : RecyclerView.Adapter<ParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(val binding: ItemHomeParentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val childAdapter = ChildAdapter(emptyList())

        init {
            binding.rvChallenge.apply {
                layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = childAdapter
            }

            updateChallengeList(childAdapter, 1L)
        }

        fun bind(category: Category) {

            // 카테고리 ID 임의 지정
            binding.layoutExercise.setOnClickListener {
                updateChallengeList(childAdapter, 1L)  // Exercise 카테고리 ID: 1
            }
            binding.layoutDevelop.setOnClickListener {
                updateChallengeList(childAdapter, 2L)  // Self-development 카테고리 ID: 2
            }
            binding.layoutHobby.setOnClickListener {
                updateChallengeList(childAdapter, 3L)  // Hobby 카테고리 ID: 3
            }
            binding.layoutStudy.setOnClickListener {
                updateChallengeList(childAdapter, 4L)  // Study 카테고리 ID: 4
            }
        }

        private fun updateChallengeList(childAdapter: ChildAdapter, categoryId: Long) {
            val challenges = challengesByCategory[categoryId] ?: emptyList()
            childAdapter.updateData(challenges)
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

    override fun getItemCount(): Int = categories.size
}