package com.example.multifunctioncalculator.convertor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import com.example.multifunctioncalculator.GridViewModal
import com.example.multifunctioncalculator.R

class ConvertorOptions : AppCompatActivity() {
    //grid view
    private lateinit var courseGRV: GridView
    private lateinit var gridviewList: List<GridViewModal>
    //list of conversion options
    val options = arrayListOf("Length","Time","Mass","Area","Volume","Speed"
        ,"Temperature","Numeral System","Pressure")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convertor_options)

        courseGRV = findViewById(R.id.idGRV)
        gridviewList = ArrayList<GridViewModal>()
        //adding text and img
        for(x in options){
            gridviewList = gridviewList + GridViewModal(x, R.drawable.ic_launcher_background)
        }
        //adapter
        val courseAdapter = GridRVAdapter(gridviewList = gridviewList, this@ConvertorOptions)
        courseGRV.adapter = courseAdapter
        //item clicked
        courseGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            //start convertor
            var i : Intent = Intent(this,ConvertorPage::class.java)
                .putExtra("PositionClicked",position)
            startActivity(i)
        }

    }
}