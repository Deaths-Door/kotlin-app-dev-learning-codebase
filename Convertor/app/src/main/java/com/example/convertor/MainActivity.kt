package com.example.convertor

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import kotlinx.android.synthetic.main.gridview_item.view.*

class MainActivity : AppCompatActivity() {
    //grid view
    private lateinit var courseGRV: GridView
    private lateinit var gridviewList: List<GridViewModal>
    //list of conversion options
    val options = arrayListOf("Length","Time","Mass","Area","Volume","Speed"
        ,"Temperature","Numeral System","Pressure")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        courseGRV = findViewById(R.id.idGRV)
        gridviewList = ArrayList<GridViewModal>()
        //adding text and img
        for(x in options){
            gridviewList = gridviewList + GridViewModal(x, R.drawable.ic_launcher_background)
        }
        //adapter
        val courseAdapter = GridRVAdapter(gridviewList = gridviewList, this@MainActivity)
        courseGRV.adapter = courseAdapter

        //item clicked
        courseGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //start convertor
            var i : Intent = Intent(this,Convertor::class.java)
                .putExtra("PositionClicked",position)
                //.putExtra("ItemClicked",options[position])
            /*courseGRV[position].idTVCourse.toString()*/
            /* .apply {
                putExtra("ItemClicked",options[position])
            }*/
            startActivity(i)
        }
    }
}