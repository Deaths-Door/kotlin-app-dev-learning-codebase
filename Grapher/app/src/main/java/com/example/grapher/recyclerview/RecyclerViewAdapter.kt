package com.example.grapher.recyclerview

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.grapher.R
import kotlinx.android.synthetic.main.recycler_view_item_list.view.*

class RecyclerViewAdapter(private val rvList: List<RvItem>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val li : LayoutInflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //custom layout
        val itemView = li.inflate(R.layout.recycler_view_item_list,parent,false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemViewModel = rvList[position]
        holder.showColor.setBackgroundColor(Color.RED)
        holder.equationGraph.text = itemViewModel.equation
    }

    override fun getItemCount(): Int = rvList.size

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val showColor: View = itemView.preview_selected_color
        val equationGraph: TextView = itemView.etEquation
    }
}