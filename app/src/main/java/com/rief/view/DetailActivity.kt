package com.rief.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rief.adapter.ViewPagerAdapter
import com.rief.databinding.ActivityDetailBinding
import com.rief.viewmodel.DetailActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityDetailBinding
	private lateinit var model : DetailActivityViewModel
	private lateinit var username: String
	private var _toCheck = false
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)
		supportActionBar!!.title = "Detail Pengguna"
		
		username = intent.getStringExtra(EXTRA_USERNAME).toString()
		val id = intent.getIntExtra(EXTRA_ID, 0)
		val avatar = intent.getStringExtra(EXTRA_AVATAR).toString()
		
		
		model = ViewModelProvider(this)[DetailActivityViewModel::class.java]
		model.getState().observe(this){
			loadingTime(it)
		}
		
		model.setDetailFromUser(username)
		model.getDetailFromUser().observe(this) {
			val nameOfUser = it.name
			val usernameOfUser = "@${it.username}"
			val followersOfUser = "${it.followers} Followers"
			val followingOfUser = "${it.following} Following"
			
			if(it != null) {
				binding.apply {
					tvName.text = nameOfUser
					tvUsername.text = usernameOfUser
					tvFollowers.text = followersOfUser
					tvFollowing.text = followingOfUser
					Glide.with(this@DetailActivity)
						.load(it.avatar)
						.centerCrop()
						.into(tvAvatar)
				}
			} else {
				Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show()
			}
		}
		
		
		CoroutineScope(Dispatchers.IO).launch {
			val counter = model.checkID(id)
			withContext(Dispatchers.Main) {
				binding.apply {
					if(counter != null) {
						if (counter > 0) {
							btnLike.isChecked = true
							_toCheck = true
						} else {
							btnLike.isChecked = false
							_toCheck = false
						}
					} else {
						Toast.makeText(counter, "No Else To Count", Toast.LENGTH_SHORT).show()
					}
				}
			}
		}
		
		binding.apply {
			btnLike.setOnClickListener {
				_toCheck = !_toCheck
				if(_toCheck) {
					model.addUserToFavorite(username, avatar, id)
				} else {
					model.removeUserFromFavorite(id)
				}
				btnLike.isChecked = _toCheck
			}
		}
		
		val pagerAdapter = ViewPagerAdapter(this)
		binding.apply {
			viewPagerLayout.adapter = pagerAdapter
			TabLayoutMediator(
				tabLayout, viewPagerLayout
			) { tab: TabLayout.Tab, position: Int -> tab.text = model.getTittle(position) }.attach()
		}
	}
	
	fun getUname() = this.username
	
	private fun loadingTime(state : Boolean) {
		binding.apply {
			when(state) {
				true -> {
					prgBar.visibility = View.VISIBLE
				}
				false -> {
					prgBar.visibility = View.GONE
				}
			}
		}
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menu?.add(Menu.NONE, 1, Menu.NONE, "Disukai")
		menu?.add(Menu.NONE, 2, Menu.NONE, "Pengaturan Dan Tentang")
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean{
		when(item.itemId){
			1 -> {
				intent = Intent(this, FavoriteActivity::class.java).also {
					startActivity(it)
				}
			}
			2 -> {
				intent = Intent(this, SettingsAndAboutActivity::class.java).also {
					startActivity(it)
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}
	
	companion object {
		const val EXTRA_USERNAME = "extra_username"
		const val EXTRA_ID = "extra_id"
		const val EXTRA_AVATAR = "extra_avatar"
	}
}