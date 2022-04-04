package com.rief.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rief.databinding.ActivitySettingsAndAboutBinding
import com.rief.viewmodel.ViewModelFactory
import com.rief.repository.PreferencesRepository
import com.rief.viewmodel.SettingsAndAboutAcitivityViewModel

class SettingsAndAboutActivity : AppCompatActivity() {
	private lateinit var binding: ActivitySettingsAndAboutBinding
	private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivitySettingsAndAboutBinding.inflate(layoutInflater)
		setContentView(binding.root)
		supportActionBar!!.title = "Pengaturan Dan Tentang"
		
		val pref = PreferencesRepository.getInstance(dataStore)
		val themes = ViewModelProvider(this, ViewModelFactory(pref))[SettingsAndAboutAcitivityViewModel::class.java]
		
		themes.getThemeSetting().observe(this) {
			isDarkActive : Boolean ->
			when(isDarkActive) {
				true-> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
					binding.btnSwitchTheme.isChecked = true
				}
				false -> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
					binding.btnSwitchTheme.isChecked = false
				}
			}
		}
		
		binding.apply {
			btnSwitchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked : Boolean ->
				themes.setThemeSetting(isChecked)
			}
		}
	}
	
	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menu?.add(Menu.NONE, 1, Menu.NONE, "Disukai")
		return super.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean{
		when(item.itemId){
			1 -> {
				intent = Intent(this, FavoriteActivity::class.java).also {
					startActivity(it)
				}
			}
		}
		return super.onOptionsItemSelected(item)
	}
}