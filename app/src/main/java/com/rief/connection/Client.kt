package com.rief.connection

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Client {
	private const val BASE_URI = "https://api.github.com/"
	
	private val interceptor = HttpLoggingInterceptor()
		.setLevel(HttpLoggingInterceptor.Level.BODY)
	
	private val okHttpClient = OkHttpClient.Builder()
		.addInterceptor(interceptor)
		.connectTimeout(30, TimeUnit.SECONDS)
		.writeTimeout(30, TimeUnit.SECONDS)
		.readTimeout(30, TimeUnit.SECONDS)
		.build()
	
	private val client = Retrofit.Builder()
		.client(okHttpClient)
		.baseUrl(BASE_URI)
		.addConverterFactory(GsonConverterFactory.create())
		.build()
	
	val api = client.create(ConnectionClient::class.java)
}