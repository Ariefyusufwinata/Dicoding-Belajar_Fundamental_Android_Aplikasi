package com.rief.github.data.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
	entities = [FavoriteUser::class],
	version = 1
)
abstract class UserDatabase : RoomDatabase() {
	
	companion object{
		var INSTACE : UserDatabase? = null
		
		fun getDatabase(context: Context): UserDatabase?{
			if(INSTACE == null){
				synchronized(UserDatabase::class) {
					INSTACE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database").build()
				}
			}
			return INSTACE
		}
	}
	
	abstract fun favoriteUserDao() : FavoriteUserDAO
}