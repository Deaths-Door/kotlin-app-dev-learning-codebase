package com.example.sqlitedatabasetodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.example.sqlitedatabasetodolist.dataBase.MyDbHelper
import com.example.sqlitedatabasetodolist.dataBase.TodoTable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val todos = ArrayList<Todo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //adapter
        val todoAdapter = ArrayAdapter<Todo>(this,
            android.R.layout.simple_list_item_1,android.R.id.text1,todos)

        lvTODOS.adapter = todoAdapter
        val db = MyDbHelper(this).writableDatabase//.readableDatabase//.writableDatabase
        fun refreshTODOlist(){
            val todoList = TodoTable.getAllTODO(db)
            todos.clear()
            todos.addAll(todoList)
            todoAdapter.notifyDataSetChanged()
        }
        refreshTODOlist()
        btnAddTODO.setOnClickListener {
            //new todo
            val newTODO = Todo(etNewTODO.text.toString(),false)
            TodoTable.insertTODO(db,newTODO)
            refreshTODOlist()

            //todos.add(newTODO)
            //todoAdapter.notifyDataSetChanged()
        }
    }
}