package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val db by lazy{
        //can use in main thread now
        Room.databaseBuilder(this,AppDataBase::class.java,"User.db")
            .allowMainThreadQueries()
            .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insert(User("AARAV","+491231321","IDK",15))
            }
            //db.userDao().insert(User("AARAV","+491231321","IDK",15))
        }
        /*button2.setOnClickListener {
            //livedata so no need
            // runBlocking {
            //val list : Deferred<List<User>> = GlobalScope.async(Dispatchers.IO){db.userDao().getAllUser()}
            // }
            //val list :List<User> = db.userDao().getAllUser()
            /*if(list.isNotEmpty()){
                with(list[0]){
                    textView.text = name
                    textView2.text = age.toString()
                    textView3.text = address
                    textView4.text = number
                }
            }*/
        }*/
        //livedata soo no need
        db.userDao().getAllUser().observe(this, Observer {list ->
            if(list.isNotEmpty()){
                with(list[0]){
                    textView.text = name
                    textView2.text = age.toString()
                    textView3.text = address
                    textView4.text = number
                }
            }
        })
    }
}