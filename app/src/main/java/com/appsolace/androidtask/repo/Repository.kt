package com.appsolace.androidtask.repo

import androidx.lifecycle.MutableLiveData
import com.appsolace.androidtask.model.UserResponseModel
import com.appsolace.androidtask.network_retrofit.ApiClient
import com.appsolace.androidtask.network_retrofit.ApiDataService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    //get Users list from API
    fun get_users(listener: ResponseListener): MutableLiveData<List<UserResponseModel>> {
        ///ini Retrofit Class
        val country_modelist = MutableLiveData<List<UserResponseModel>>()
        val apiInterface = ApiClient.apiClient!!.create(ApiDataService::class.java)
        val call = apiInterface.getusers()
        call!!.enqueue(object : Callback<List<UserResponseModel>> {

            override fun onResponse(
                call: Call<List<UserResponseModel>>,
                response: Response<List<UserResponseModel>>
            ) {
                country_modelist.postValue(response.body()!!)
            }

            override fun onFailure(call: Call<List<UserResponseModel>>, t: Throwable) {

                listener.onError(t.message.toString())
            }
        })


        return country_modelist
    }


}