package com.rief.model

import com.google.gson.annotations.SerializedName

data class ModelDetailUser(
	@SerializedName("id")
	val id:Int?,
	@SerializedName("avatar_url")
	val avatar: String?,
	@SerializedName("login")
	val username: String?,
	val name: String?,
	val followers: Int,
	val following: Int,
	val followers_url: String,
	val following_url: String
)