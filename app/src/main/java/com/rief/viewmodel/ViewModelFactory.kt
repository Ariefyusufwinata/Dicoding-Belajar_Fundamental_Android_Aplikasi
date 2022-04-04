package com.rief.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rief.repository.PreferencesRepository
import java.lang.IllegalArgumentException

class ViewModelFactory(private val pref : PreferencesRepository)  : ViewModelProvider.NewInstanceFactory() {
	
	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>) : T {
		if(modelClass.isAssignableFrom(SettingsAndAboutAcitivityViewModel::class.java)) {
			return SettingsAndAboutAcitivityViewModel(pref) as T
		}
		throw IllegalArgumentException("Unknown ViewModel Class : " + modelClass.name)
	}
}