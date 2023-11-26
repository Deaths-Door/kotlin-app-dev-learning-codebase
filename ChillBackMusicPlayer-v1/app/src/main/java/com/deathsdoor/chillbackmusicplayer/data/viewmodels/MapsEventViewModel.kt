package com.deathsdoor.chillbackmusicplayer.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deathsdoor.chillbackmusicplayer.data.dataclasses.app.UserEventInfo

class MapsEventViewModel: ViewModel() {
    val userEvents = MutableLiveData<List<UserEventInfo>>()
}