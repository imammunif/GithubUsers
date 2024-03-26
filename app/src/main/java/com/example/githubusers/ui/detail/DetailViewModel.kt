package com.example.githubusers.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.data.response.DetailUserResponse
import com.example.githubusers.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _profileUser = MutableLiveData<DetailUserResponse>()
    val profileUser: LiveData<DetailUserResponse> = _profileUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _profileUser.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}