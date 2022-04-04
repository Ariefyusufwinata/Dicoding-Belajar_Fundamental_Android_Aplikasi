package com.rief.view

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rief.adapter.MainAdapter
import com.rief.databinding.ActivityMainBinding
import com.rief.model.ModelUser
import com.rief.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {
	private lateinit var binding : ActivityMainBinding
	private lateinit var connector : MainAdapter
	private lateinit var model : MainActivityViewModel
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		supportActionBar!!.title = "Aplikasi Pengguna Github"
		
		connector = MainAdapter()
		connector.notifyDataSetChanged()
		
		connector.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
			override fun onItemClicked(data: ModelUser) {
				Intent(this@MainActivity, DetailActivity::class.java).also {
					it.putExtra(DetailActivity.EXTRA_ID, data.id)
					it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
					it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar)
					startActivity(it)
				}
			}
		})
		model = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainActivityViewModel::class.java] //.get(ViewModel)
		
		binding.apply {
			rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
			rvUser.setHasFixedSize(true)
			rvUser.adapter = connector
			
			btnCari.setOnClickListener {
				searchUser()
			}
			
			edtSearch.setOnKeyListener { _, i, keyEvent ->
				return@setOnKeyListener keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER
			}
			edtSearch.setOnEditorActionListener { _, i, keyEvent ->
				if (i == EditorInfo.IME_ACTION_SEARCH) {
					searchUser()
				}
				true
			}
		}
		
		model.getValueOfSearch().observe(this) {
			when {
				it != null -> {
					connector.setList(it)
					loadingTime(false)
				}
			}
			if(it.size == 0) {
				Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
				loadingTime(false)
			}
		}
	}
	
	private fun searchUser(){
		val search:String
		binding.apply{
			search = edtSearch.text.toString()
			if(search.isEmpty()) {
				return
			}
			loadingTime(true)
			model.setSearch(search)
		}
	}
	
	private fun loadingTime(isTrue : Boolean) {
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
}