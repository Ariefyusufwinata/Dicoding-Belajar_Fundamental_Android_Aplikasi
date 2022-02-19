package com.rief.github.data.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite_user")
data class FavoriteUser (
	val login: String?,
	@PrimaryKey
	val id: Int?
) : Serializable