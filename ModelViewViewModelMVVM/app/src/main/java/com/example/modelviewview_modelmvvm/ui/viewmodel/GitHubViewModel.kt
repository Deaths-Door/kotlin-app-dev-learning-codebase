package com.example.modelviewview_modelmvvm.ui.viewmodel

import androidx.lifecycle.*
import com.example.modelviewview_modelmvvm.data.models.UserItem
import com.example.modelviewview_modelmvvm.data.repos.GitHubRepository
import kotlinx.coroutines.*

class GitHubViewModel: ViewModel() {

    val users = MutableLiveData<List<UserItem>>()
    //all users
    fun fetchUsers(){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){ GitHubRepository.getUsers() }
            if(response.isSuccessful){
                response.body()?.let {
                    //send data somewhere
                    users.postValue(it)
                }
            }
        }
        /*runIo(Dispatchers.Default) {

        }*/
    }

    fun searchUsers(name:String)/* or */ = liveData(Dispatchers.IO){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){ GitHubRepository.searchUsers(name) }
            if(response.isSuccessful){
                response.body()?.let {
                    //send data somewhere
                   // users.postValue(it.items as List<User>?)
                    emit(it.items)
                }
            }
        }
        /*runIo(Dispatchers.Default) {

        }*/
    }
}


/**extension function for [ViewModel] scope**/
fun ViewModel.runIo(
    /*Custom type of dispatchers is what thread or threads the corresponding coroutine uses for its execution*/
    dispatchers: CoroutineDispatcher = Dispatchers.IO,function:suspend CoroutineScope.() -> Unit){
    /*
    suspend fun a(){
        GlobalScope.async {

        }
    }
    */
    //equals this
    viewModelScope.launch(dispatchers) {
        function()
    }
}