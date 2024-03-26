package com.example.githubusers.data.retrofit

import com.example.githubusers.data.response.DetailUserResponse
import com.example.githubusers.data.response.GithubResponse
import com.example.githubusers.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    //    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(@Query("q") login: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowerList(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowingList(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}
