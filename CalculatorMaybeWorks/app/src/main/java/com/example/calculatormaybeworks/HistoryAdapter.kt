package com.example.calculatormaybeworks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_history.view.*

class HistoryAdapter(private val history: ArrayList<CalcHistory>): RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val li : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //custom layout
        val itemView = li.inflate(R.layout.list_item_history,parent,false)
        return HistoryViewHolder(itemView)
    }
    override fun getItemCount(): Int = history.size
    override fun onBindViewHolder(holder : HistoryViewHolder,position:Int){
        holder?.tvHistoryEquation?.text = history[position].equation
        holder?.tvHistoryAnswer?.text = history[position].answer
    }
    class HistoryViewHolder(private val item: View) : RecyclerView.ViewHolder(item){
        val tvHistoryEquation: TextView = item.tvHistoryEquation
        val tvHistoryAnswer: TextView = item.tvHistoryAnswer
    }
}
