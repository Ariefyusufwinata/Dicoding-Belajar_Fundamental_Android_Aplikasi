package com.rief.view

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.rief.R
import com.rief.repository.PreferencesRepository
import com.rief.viewmodel.SettingsAndAboutAcitivityViewModel
import com.rief.viewmodel.ViewModelFactory

class SplashScreenActivity : AppCompatActivity() {
	private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		val pref = PreferencesRepository.getInstance(dataStore)
		val themes = ViewModelProvider(this, ViewModelFactory(pref))[SettingsAndAboutAcitivityViewModel::class.java]

		themes.getThemeSetting().observe(this) {
			isDarkActive : Boolean ->
			when(isDarkActive) {
				true-> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				}
				false -> {
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				}
			}
		}
		
		Handler(mainLooper).postDelayed({
			Intent(this, MainActivity::class.java).also {
				it.flags = FLAG_ACTIVITY_SINGLE_TOP
				startActivity(it)
			}
			finish()
		}, delay)
	}
	
	companion object{
		const val delay:Long = 1500
	}
}