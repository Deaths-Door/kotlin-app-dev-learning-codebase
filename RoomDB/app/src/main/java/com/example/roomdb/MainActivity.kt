package com.example.roomdb

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "WOW") val wow:Wow
)
data class Wow(val a: String,val b:String)

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll():LiveData<List<User>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user:User)
}
class TypeConverterWOW(){
    @TypeConverter
    fun fromWow(wow:Wow):String = Gson().toJson(wow)
    @TypeConverter
    fun toWow(wowString:String):Wow = Gson().fromJson(wowString,object :TypeToken<Wow>(){}.type)
}
@Database(entities = [User::class], version = 1)
@TypeConverters(TypeConverterWOW::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        //singleton prevents multiple instance of database opened at the same time
        @Volatile
        private var INSTANCE : AppDatabase? = null
        fun getDataBase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "database-name").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test(this,this)
    }
}
fun test(context: Context,mainActivity: MainActivity){
    GlobalScope.async {
    AppDatabase.getDataBase(context).userDao().insertAll(User(2,"NewUser","FICK", Wow("WO","RKS")))}
    AppDatabase.getDataBase(context).userDao().getAll().observe(mainActivity) {
        Log.i("USERDAO!", "${it}}")

    }
    GlobalScope.async {
        AppDatabase.getDataBase(context).userDao().update(User(2,"UPDATED USER","WORKS??", Wow("WO","RKS")))
        AppDatabase.getDataBase(context).userDao().update(User(1,"UPDATED USER","WORKS??", Wow("WO","RKS")))

    }
}

