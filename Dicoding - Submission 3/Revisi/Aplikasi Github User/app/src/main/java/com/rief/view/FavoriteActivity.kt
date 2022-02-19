package com.rief.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rief.viewmodel.FavoriteActivityViewModel
import com.rief.adapter.MainAdapter
import com.rief.database.EntitasFavorite
import com.rief.databinding.ActivityFavoriteBinding
import com.rief.model.ModelUser

class FavoriteActivity : AppCompatActivity() {
	private  lateinit var binding : ActivityFavoriteBinding
	private lateinit var model : FavoriteActivityViewModel
	private lateinit var connector : MainAdapter
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityFavoriteBinding.inflate(layoutInflater)
		setContentView(binding.root)
		supportActionBar!!.title = "Disukai"
		
		connector = MainAdapter()
		connector.notifyDataSetChanged()
		
		model = ViewModelProvider(this)[FavoriteActivityViewModel::class.java]
		connector.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
			override fun onItemClicked(data: ModelUser) {
				Intent(this@FavoriteActivity, DetailActivity::class.java).also {
					it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
					it.putExtra(DetailActivity.EXTRA_ID, data.id)
					it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar)
					startActivity(it)
				}
			}
		})
		
		binding.apply {
			rvUser.setHasFixedSize(true)
			rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
			rvUser.adapter = connector
		}
		
		model.getFromFavorite()?.observe(this) {
			if(it != null) {
				val setData = mappingData(it)
				connector.setList(setData)
			}
		}
	}
	
	private fun mappingData(users : List<EntitasFavorite>) : ArrayList<ModelUser> {
		val listFromUser = ArrayList<ModelUser>()
		for(a in users){
			val map = ModelUser(
				a.id,
				a.avatar,
				a.username
			)
			listFromUser.add(map)
		}
		return listFromUser
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menu?.add(Menu.NONE, 1, Menu.NONE, "Pengaturan Dan Tentang")
		return super.onCreateOptionsMenu(menu)
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean{
		when(item.itemId){
			1 -> {
				intent = Intent(this, SettingsAndAboutActivity::class.java).also {
					startActivity(it)
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}
}