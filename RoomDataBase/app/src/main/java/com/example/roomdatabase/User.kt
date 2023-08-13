package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

//automatically creates database

@Entity
data class User(val name:String,val number:String,val address: String,val age:Int,
                @PrimaryKey(autoGenerate = true)
                val id:Long = 0L)