package com.rief.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rief.database.DatabaseUser
import com.rief.database.EntitasFavorite
import com.rief.database.FavoriteUserDAO

class FavoriteActivityViewModel(application: Application) : AndroidViewModel(application) {
	private var dao: FavoriteUserDAO?
	private var db : DatabaseUser? = DatabaseUser.getDataFromDatabase(application)
	
	fun getFromFavorite() : LiveData<List<EntitasFavorite>>? {
		return dao?.getAllUserFromFavorite()
	}
	
	init {
		db = DatabaseUser.getDataFromDatabase(application)
		dao = db?.favoriteUser()
	}
}