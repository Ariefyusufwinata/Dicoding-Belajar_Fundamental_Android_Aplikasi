package com.rief.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesRepository private constructor(private val dataStore: DataStore<Preferences>) {
	
	private val KEYTHEME = booleanPreferencesKey("theme_settings")
	
	fun getTheme() : Flow<Boolean> {
		return dataStore.data.map { preferences ->
			preferences[KEYTHEME] ?: false
		}
	}
	
	suspend fun setTheme(isDark : Boolean) {
		dataStore.edit { prefrences ->
			prefrences[KEYTHEME] = isDark
		}
	}
	
	companion object {
		@Volatile
		private var INSTANCE : PreferencesRepository? = null
		
		fun getInstance(dataStore: DataStore<Preferences>) : PreferencesRepository {
			return INSTANCE ?: synchronized(this) {
				val instance = PreferencesRepository(dataStore)
				INSTANCE = instance
				instance
			}
		}
	}
}