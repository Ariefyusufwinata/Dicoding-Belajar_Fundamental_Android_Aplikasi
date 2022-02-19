package com.rief.github.data.model.response

data class DetailUserResponse(
    val id: Int?,
    val avatar_url: String?,
    val login: String?,
    val name: String?,
    val following: Int?,
    val followers: Int?,
    val following_url: String?,
    val followers_url: String?
)
