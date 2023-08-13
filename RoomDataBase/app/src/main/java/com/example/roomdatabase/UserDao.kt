package com.example.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert//(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Insert
    fun insertAll(list: List<User>)
    @Delete
    fun delete(user : User)
    @Query("SELECT * FROM User")
    fun getAllUser(): LiveData<List<User>>
    @Query("Select * FROM USER WHERE age >= :age")
    fun getUserAge(age:Int):List<User>
}


