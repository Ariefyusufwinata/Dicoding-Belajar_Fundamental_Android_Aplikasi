package com.rief.github.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rief.github.data.api.RetrofitClient
import com.rief.github.data.model.response.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val user = MutableLiveData<DetailUserResponse>()

    val stateLoad = MutableLiveData<Boolean>()
    
    fun setUserDetail(username : String){
        stateLoad.postValue(true)
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if(response.isSuccessful){
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

    fun getUserDetail(): LiveData<DetailUserResponse>{
        return user
    }
    
    fun getStateLoad(): LiveData<Boolean>{
        return stateLoad
    }
}