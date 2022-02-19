package com.rief.github.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rief.databinding.ActivityDetailUserBinding
import com.rief.github.view.adapter.SecPagerAdapt
import com.rief.github.view.viewmodel.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
    
        val username = intent.getStringExtra(EXTRA_USERNAME).toString()
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val bundl = Bundle()
        bundl.putString(EXTRA_USERNAME, username)
        setContentView(binding.root)
        
        viewModel.getStateLoad().observe(this, {
            showLoading(it)
        })
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe( this, {

            val name = it.name
            val usernameLogin = "@${it.login}"
            val followers = "${it.followers} Followers"
            val following = "${it.following} Following"

            if(it != null){
                binding.apply {
                    tvName.text = name
                    tvUsername.text = usernameLogin
                        tvFollowers.text = followers
                        tvFollowing.text = following
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(tvAvatar)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if(count > 0){
                        binding.btnFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.btnFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }
        
        binding.btnFavorite.setOnClickListener{
            _isChecked = !_isChecked
            if(_isChecked){
                viewModel.addFavorite(username, id)
            } else {
                viewModel.removeFavorite(id)
            }
            binding.btnFavorite.isChecked = _isChecked
        }
        
        val secPagerAdapt = SecPagerAdapt(this,supportFragmentManager, bundl)
        binding.apply {
            viewPager.adapter = secPagerAdapt
            tabFF.setupWithViewPager(viewPager)
        }

    }
    
    private fun showLoading(state: Boolean){
        if(state){
            binding.proggresBar.visibility = View.VISIBLE
        } else {
            binding.proggresBar.visibility = View.GONE
        }
    }
    
    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
    }
}