package com.rief.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDAO {
	@Query("SELECT * FROM favorite_user")
	fun getAllUserFromFavorite(): LiveData<List<EntitasFavorite>>
	
	@Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
	fun getUserFromFavorite(id:Int) : Int
	
	@Insert
	fun addUserToFavorite(favoriteUser : EntitasFavorite)
	
	@Query ("DELETE FROM favorite_user WHERE favorite_user.id = :id")
	fun deleteUserFromFavorite(id:Int) : Int
}