package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class TodoAdapter(private val list: List<TodoModel>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo,parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
    class TodoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: TodoModel) {
            with(itemView){
                //get colors
                val colors = resources.getIntArray(R.array.random_color)
                //get random color
                val randomColor = colors[Random().nextInt(colors.size)]
                viewColorTag.setBackgroundColor(randomColor)
                tvShowTitle.text = todoModel.title
                tvShowTask.text = todoModel.description
                tvShowCategory.text = todoModel.category
                updateTime(todoModel.time)
                updateDate(todoModel.date)
            }
        }
        private fun updateTime(time: Long) {
            //format of time -> Mon,5 Jan 2020
            val myFormat = "h:mm"
            val sdf = SimpleDateFormat(myFormat)
            //setting text
            itemView.tvShowTime.text = sdf.format(Date(time))

        }
        private fun updateDate(time: Long) {
            //format of time -> Mon,5 Jan 2020
            val myFormat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myFormat)
            //setting text
            itemView.tvShowDate.text = sdf.format(Date(time))

        }
    }
}