package com.rief.github.data.api


import com.rief.BuildConfig
import com.rief.github.data.model.response.DetailUserResponse
import com.rief.github.data.model.User
import com.rief.github.data.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.token}")
    fun getSearchUsers(
        @Query("q") query:String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.token}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.token}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.token}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}