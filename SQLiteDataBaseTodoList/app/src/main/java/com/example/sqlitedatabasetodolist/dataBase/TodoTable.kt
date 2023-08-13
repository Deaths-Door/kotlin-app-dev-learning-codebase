package com.example.sqlitedatabasetodolist.dataBase

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitedatabasetodolist.Todo

object TodoTable {
    val TABLE_NAME = "todos"
    object Columns{
        val ID = "id"
        val TASK = "task"
        val DONE = "done"
    }
    val CMD_CREATE_TABLE = """CREATE TABLE IF NOT EXISTS $TABLE_NAME
        (${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Columns.TASK} TEXT,
        ${Columns.DONE} BOOLEAN);
    """.trimIndent()

    fun insertTODO(db: SQLiteDatabase,todo : Todo){
        val row =ContentValues()
        //id are auto done
        row.put(Columns.TASK,todo.task)
        row.put(Columns.DONE,todo.done)

        //insert
        db.insert(TABLE_NAME,null,row)

    }
    fun getAllTODO(db: SQLiteDatabase) : ArrayList<Todo>{
        val todos = ArrayList<Todo>()
        //cursor is which row and column ur accessing
        //like the green box in excel that shows what box uve selected
        var cursor = db.query(
            TABLE_NAME, arrayOf(Columns.ID,Columns.TASK,Columns.DONE),
            null,null,null,null,null)

        //till last
        while(cursor.moveToNext()){
            //get task and done
            val todo = Todo(cursor.getString(1),cursor.getInt(2) == 1)
            //add todos to Todo
            todos.add(todo)
        }
        return todos
    }
}