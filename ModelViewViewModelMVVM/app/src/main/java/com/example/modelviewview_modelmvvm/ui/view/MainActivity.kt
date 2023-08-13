package com.example.modelviewview_modelmvvm.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modelviewview_modelmvvm.R
import com.example.modelviewview_modelmvvm.data.models.UserItem
import com.example.modelviewview_modelmvvm.ui.adapter.UsersAdapter
import com.example.modelviewview_modelmvvm.ui.viewmodel.GitHubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //intialize view model
    val vm by lazy{
        ViewModelProvider(this)[GitHubViewModel::class.java]
    }
    val list = arrayListOf<UserItem>()
    val adapter = UsersAdapter(list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        vm.fetchUsers()
        vm.users.observe(this, Observer {
            if(!it.isNullOrEmpty()){
                list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
    }
}