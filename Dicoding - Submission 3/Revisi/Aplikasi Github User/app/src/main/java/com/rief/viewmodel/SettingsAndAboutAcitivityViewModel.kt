package com.rief.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rief.repository.PreferencesRepository
import kotlinx.coroutines.launch

class SettingsAndAboutAcitivityViewModel(private val pref : PreferencesRepository) : ViewModel() {
	fun getThemeSetting() : LiveData<Boolean> {
		return pref.getTheme().asLiveData()
	}
	
	fun setThemeSetting(isDark : Boolean) {
		viewModelScope.launch {
			pref.setTheme(isDark)
		}
	}
}