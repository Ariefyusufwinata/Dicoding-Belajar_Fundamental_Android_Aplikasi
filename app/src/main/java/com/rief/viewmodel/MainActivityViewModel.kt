package com.rief.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.rief.connection.Client
import com.rief.model.ModelArrUser
import com.rief.model.ModelUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel: ViewModel(){
	val list = MutableLiveData<ArrayList<ModelUser>>()
	
	fun getValueOfSearch() : LiveData<ArrayList<ModelUser>> {
		return list
	}
	
	fun setSearch(query : String){
		Client.api
			.getFromSearchUsers(query)
			.enqueue(object : Callback<ModelArrUser>{
				override fun onResponse(
					call: Call<ModelArrUser>,
					response: Response<ModelArrUser>
				) {
					if(response.isSuccessful) {
						list.postValue(response.body()?.dataUser)
					}
				}
				
				override fun onFailure(call: Call<ModelArrUser>, t: Throwable) {
					Log.e("Fail Internal Error", t.message.toString())
				}
			})
	}
}