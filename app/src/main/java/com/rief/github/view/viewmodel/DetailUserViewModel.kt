package com.rief.github.view.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rief.github.data.api.RetrofitClient
import com.rief.github.data.model.database.FavoriteUser
import com.rief.github.data.model.database.FavoriteUserDAO
import com.rief.github.data.model.database.UserDatabase
import com.rief.github.data.model.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
	val user = MutableLiveData<DetailUserResponse>()
	val stateLoad = MutableLiveData<Boolean>()
	
	private var userDao : FavoriteUserDAO?
	private var userDb : UserDatabase?
	
	init {
		userDb = UserDatabase.getDatabase(application)
		userDao = userDb?.favoriteUserDao()
	}
	
	fun setUserDetail(username: String) {
		stateLoad.postValue(true)
		RetrofitClient.apiInstance
			.getUserDetail(username)
			.enqueue(object : Callback<DetailUserResponse> {
				override fun onResponse(
					call: Call<DetailUserResponse>,
					response: Response<DetailUserResponse>
				) {
					if (response.isSuccessful) {
						user.postValue(response.body())
						stateLoad.postValue(false)
					}
				}
				
				override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
					Log.d("Debuging", t.message.toString())
					stateLoad.postValue(false)
				}
				
			})
	}
	
	fun getUserDetail(): LiveData<DetailUserResponse> {
		return user
	}
	
	fun getStateLoad(): LiveData<Boolean> {
		return stateLoad
	}
	
	fun addFavorite(username:String, id:Int){
		CoroutineScope(Dispatchers.IO).launch {
			var user =  FavoriteUser(
				username    ,
				id
			)
			userDao?.addFavorite(user)
		}
	}
	
	suspend fun  checkUser(id:Int) = userDao?.checkedUser(id)
	
	fun removeFavorite(id:Int){
		CoroutineScope(Dispatchers.IO).launch {
			userDao?.removeUser(id)
		}
	}
}