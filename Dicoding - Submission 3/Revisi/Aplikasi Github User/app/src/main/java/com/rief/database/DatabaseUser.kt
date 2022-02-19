package com.rief.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
	entities = [EntitasFavorite::class],
	version = 1
)
abstract class DatabaseUser : RoomDatabase(){
	
	abstract fun favoriteUser(): FavoriteUserDAO
	
	companion object{
		private var INSTACE : DatabaseUser? = null
		
		fun getDataFromDatabase(context: Context): DatabaseUser?{
			if(INSTACE == null) {
				synchronized(DatabaseUser::class) {
					INSTACE = Room.databaseBuilder(context.applicationContext, DatabaseUser::class.java, "user_database").build()
				}
			}
			return INSTACE
		}
	}
}