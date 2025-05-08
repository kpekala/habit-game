package com.example.habitgamenative.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TasksFragment()
            1 -> PlayerFragment()
            2 -> ShopFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}