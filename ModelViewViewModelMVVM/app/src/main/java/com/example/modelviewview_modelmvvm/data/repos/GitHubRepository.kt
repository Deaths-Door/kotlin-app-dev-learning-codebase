package com.example.modelviewview_modelmvvm.data.repos

import com.example.modelviewview_modelmvvm.data.api.Client


object GitHubRepository {
    suspend fun getUsers() = Client.api.getUsers()
    suspend fun searchUsers(name : String) = Client.api.searchUsers(name)
}