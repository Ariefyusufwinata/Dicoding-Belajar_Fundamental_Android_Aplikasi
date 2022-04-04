package com.rief.connection

import com.rief.model.ModelDetailUser
import com.rief.model.ModelArrUser
import com.rief.model.ModelUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectionClient {
	@GET("search/users")
	@Headers("Authorization: token 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")
	fun getFromSearchUsers(
		@Query("q") query:String
	): Call<ModelArrUser>
	
	@GET("users/{username}")
	@Headers("Authorization: token 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")
	fun getFromUserDetail(
		@Path("username") username:String
	): Call<ModelDetailUser>
	
	@GET("users/{username}/followers")
	@Headers("Authorization: token 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")
	fun getFromFollowersUser(
		@Path("username") username:String
	): Call <ArrayList<ModelUser>>
	
	@GET("users/{username}/following")
	@Headers("Authorization: token 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")
	fun getFromFollowingUser(
		@Path("username") username:String
	): Call <ArrayList<ModelUser>>
}