package com.example.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoModel::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun todoDao(): TodoDao
    companion object{
        //singleton prevents multiple instance of database opened at the same time
        @Volatile
        private var INSTANCE : AppDataBase? = null
        fun getDataBase(context: Context): AppDataBase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance =
                    Room.databaseBuilder(context.applicationContext,
                        AppDataBase::class.java,
                        Db_Name).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}