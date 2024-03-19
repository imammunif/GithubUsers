package com.example.githubusers.data.retrofit

import com.example.githubusers.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(@Query("q") login: String): Call<GithubResponse>
}
