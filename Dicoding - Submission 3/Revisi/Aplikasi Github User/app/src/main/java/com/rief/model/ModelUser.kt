package com.rief.model

import com.google.gson.annotations.SerializedName

data class ModelUser(
	@SerializedName("id")
	val id: Int?,
	@SerializedName("login")
	val username: String?,
	@SerializedName("avatar_url")
	val avatar: String?
)
