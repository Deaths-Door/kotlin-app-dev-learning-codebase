package com.example.modelviewview_modelmvvm.data.api

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    val gson: Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    val refroit = Retrofit.Builder().baseUrl("http://api.github.com/").addConverterFactory(GsonConverterFactory.create(gson)).build()
    val api = refroit.create(GithubService::class.java)
}
