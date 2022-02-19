package com.rief.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rief.fragment.FollowersFragment
import com.rief.fragment.FollowingFragments

class ViewPagerAdapter(appCompatActivity: AppCompatActivity) : FragmentStateAdapter(appCompatActivity){

	private val listFragment : List<Fragment> =
		listOf(
			FollowersFragment(),
			FollowingFragments()
		)
	
	override fun getItemCount(): Int = listFragment.size
	override fun createFragment(position: Int): Fragment = listFragment[position]
}