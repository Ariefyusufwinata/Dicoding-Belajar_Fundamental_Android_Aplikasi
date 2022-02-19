package com.rief.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rief.connection.Client
import com.rief.model.ModelUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
	val list = MutableLiveData<ArrayList<ModelUser>>()
	
	fun getFollowers() : LiveData<ArrayList<ModelUser>> {
		return list
	}
	
	fun setFollowers(name : String) {
		Client.api
			.getFromFollowersUser(name)
			.enqueue(object : Callback<ArrayList<ModelUser>> {
				override fun onResponse(
					call: Call<ArrayList<ModelUser>>,
					response: Response<ArrayList<ModelUser>>
				) {
					if(response.isSuccessful) {
						list.postValue(response.body())
					}
				}
				override fun onFailure(call: Call<ArrayList<ModelUser>>, t: Throwable) {
					Log.e("Internal Error", t.message.toString())
				}
			})
	}
}