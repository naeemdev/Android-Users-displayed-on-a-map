package com.appsolace.androidtask.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.appsolace.androidtask.model.UserResponseModel
import com.appsolace.androidtask.repo.Repository
import com.appsolace.androidtask.repo.ResponseListener

class UserViewModel (application: Application) : AndroidViewModel(application) {
    private val mRepository: Repository

    fun getusers( listener: ResponseListener): MutableLiveData<List<UserResponseModel>> {
        return mRepository.get_users(listener)
    }

    init {
        mRepository = Repository()
    }


}