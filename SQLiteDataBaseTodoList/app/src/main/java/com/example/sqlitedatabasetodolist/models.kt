package com.example.sqlitedatabasetodolist

data class Todo(var task: String,var done: Boolean){
    //toString new job
    override fun toString(): String {
        return this.task
    }
}
