package com.rief.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rief.R
import com.rief.view.DetailActivity
import com.rief.viewmodel.FollowerViewModel
import com.rief.adapter.MainAdapter
import com.rief.databinding.FragmentFollowerFollowingBinding

class FollowersFragment : Fragment(R.layout.fragment_follower_following) {
	private lateinit var model : FollowerViewModel
	private lateinit var connector : MainAdapter
	private lateinit var name:String
	
	private var _binding : FragmentFollowerFollowingBinding? = null
	private val binding get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		connector = MainAdapter()
		connector.notifyDataSetChanged()
		
		name = (activity as DetailActivity).getUname()
		_binding = FragmentFollowerFollowingBinding.bind(view)
		
		binding.apply {
			rvUser.setHasFixedSize(true)
			rvUser.layoutManager = LinearLayoutManager(activity)
			rvUser.adapter = connector
		}
		
		loadingTime(true)
		model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
			FollowerViewModel::class.java)
		model.setFollowers(name)
		model.getFollowers().observe(viewLifecycleOwner, {
			if(it != null) {
				connector.setList(it)
				loadingTime(false)
			}
		})
	}
	
	private fun loadingTime(isTrue : Boolean){
		binding.apply {
			when(isTrue) {
				true -> {
					prgBar.visibility = View.VISIBLE
				}
				false -> {
					prgBar.visibility = View.GONE
				}
			}
		}
	}
	
	//For Destroying View Unusable Fragments
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}