package com.rief.github.data.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDAO {
	@Insert
	fun addFavorite(favoriteUser: FavoriteUser)
	
	@Query ("SELECT * FROM favorite_user")
	fun getFavoriteUser() : LiveData<List<FavoriteUser>>
	
	@Query("SELECT count(*) FROM favorite_user WHERE favorite_user.id = :id")
	fun checkedUser(id:Int) : Int
	
	@Query("DELETE FROM favorite_user WHERE favorite_user.id = :id")
	fun  removeUser(id:Int) : Int
}