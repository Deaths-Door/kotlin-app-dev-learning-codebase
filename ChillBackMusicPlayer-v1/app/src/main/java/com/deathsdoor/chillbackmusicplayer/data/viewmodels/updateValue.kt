package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import kotlinx.coroutines.flow.MutableStateFlow


fun <T> MutableStateFlow<T>.updateValue(data:T){
    this.value = data
}