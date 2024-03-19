package com.example.githubusers.data.retrofit

import com.example.githubusers.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") login: String): Call<GithubResponse>
}
