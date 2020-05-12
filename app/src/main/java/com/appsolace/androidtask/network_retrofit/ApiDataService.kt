package com.appsolace.androidtask.network_retrofit

import com.appsolace.androidtask.model.UserResponseModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiDataService {

    @GET("users")
    fun getusers( ): Call<List<UserResponseModel>>?

}