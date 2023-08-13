package com.example.calculatormaybeworks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

internal class GridRVAdapter(private val gridviewList: List<GridViewModal>, private val context: Context) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var gridviewTV: TextView
    private lateinit var gridviewIMG: ImageView

    override fun getCount(): Int = gridviewList.size
    override fun getItem(position: Int): Any? = null
    override fun getItemId(position: Int): Long = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var convertView = convertView
        //if null -> give value
        if (layoutInflater == null) {
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            //gridview item layout
            convertView = layoutInflater!!.inflate(R.layout.gridview_item, null)
        }
        gridviewIMG = convertView!!.findViewById(R.id.idIVCourse)
        gridviewTV = convertView!!.findViewById(R.id.idTVCourse)
        //set TV and img
        gridviewIMG.setImageResource(gridviewList[position].itemImg)
        gridviewTV.text = gridviewList[position].itemName
        return convertView
    }
}

