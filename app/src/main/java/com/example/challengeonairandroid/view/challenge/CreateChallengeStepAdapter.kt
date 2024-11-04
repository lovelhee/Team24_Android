package com.example.challengeonairandroid.view.challenge

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CreateChallengeStepAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CreateChallengeStep1Fragment()
            1 -> CreateChallengeStep2Fragment()
            else -> CreateChallengeStep1Fragment()
        }
    }
}