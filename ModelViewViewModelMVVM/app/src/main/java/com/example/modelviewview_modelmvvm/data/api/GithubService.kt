package com.example.modelviewview_modelmvvm.data.api

import com.example.modelviewview_modelmvvm.data.models.SearchResponse
import com.example.modelviewview_modelmvvm.data.models.UserItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("users")
    suspend fun getUsers(): Response<List<UserItem>>
    @GET("search/users")
    suspend fun searchUsers(@Query("q") name :String):Response<SearchResponse>
}
