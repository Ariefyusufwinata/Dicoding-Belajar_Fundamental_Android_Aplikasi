package com.rief.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorite_user")
data class EntitasFavorite(
	@PrimaryKey
	val id:Int,
	@SerializedName("login")
	val username: String?,
	@SerializedName("avatar_url")
	val avatar: String?
)
