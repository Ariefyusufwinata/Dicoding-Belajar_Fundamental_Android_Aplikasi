package com.rief.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rief.connection.Client
import com.rief.database.DatabaseUser
import com.rief.database.EntitasFavorite
import com.rief.database.FavoriteUserDAO
import com.rief.model.ModelDetailUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel(application: Application) : AndroidViewModel(application) {
	private var dao : FavoriteUserDAO?
	private var db : DatabaseUser? = DatabaseUser.getDataFromDatabase(application)
	
	private val user = MutableLiveData<ModelDetailUser>()
	private val stateOf = MutableLiveData<Boolean>()

	fun setDetailFromUser(name : String) {
		stateOf.postValue(true)
		Client.api
			.getFromUserDetail(name)
			.enqueue(object : Callback<ModelDetailUser>{
				override fun onResponse(
					call: Call<ModelDetailUser>,
					response: Response<ModelDetailUser>
				) {
					if(response.isSuccessful) {
						user.postValue(response.body())
						stateOf.postValue(false)
					}
				}
				
				override fun onFailure(call: Call<ModelDetailUser>, t: Throwable) {
					Log.e("Fail Internal Error", t.message.toString())
					stateOf.postValue(false)
				}
				
			})
	}
	
	fun addUserToFavorite(avatar : String, username : String, id : Int) {
		CoroutineScope(Dispatchers.IO).launch {
			val valueFromUser = EntitasFavorite(
				id,
				username,
				avatar
			)
			dao?.addUserToFavorite(valueFromUser)
		}
	}
	
	fun getState() : LiveData<Boolean> {
		return stateOf
	}
	
	fun getDetailFromUser() : LiveData<ModelDetailUser> {
		return user
	}
	
	fun checkID(id : Int) = dao?.getUserFromFavorite(id)
	
	fun removeUserFromFavorite(id : Int) {
		CoroutineScope(Dispatchers.IO).launch {
			dao?.deleteUserFromFavorite(id)
		}
	}
	
	fun getTittle(position : Int) : String {
		val tittle = listOf("Followers", "Following")
		return tittle[position]
	}
	
	init {
		dao = db?.favoriteUser()
	}
}