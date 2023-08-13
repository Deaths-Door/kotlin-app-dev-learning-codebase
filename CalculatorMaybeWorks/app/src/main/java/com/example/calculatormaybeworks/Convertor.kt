package com.example.calculatormaybeworks

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import kotlinx.android.synthetic.main.fragment_convertor.*

class Convertor : Fragment() {
    //list of conversion options
    val options = arrayListOf("Length","Time","Mass","Area","Volume","Speed"
        ,"Temperature","Numeral System","Pressure")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = inflater.inflate(R.layout.fragment_convertor,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var gridviewList = ArrayList<GridViewModal>()
        val courseGRV: GridView? = idGRV
        //adding text and img
        for(x in options){
            gridviewList += GridViewModal(x, R.drawable.ic_launcher_background)
        }
        //adapter
        val courseAdapter = GridRVAdapter(gridviewList = gridviewList,requireContext())
        courseGRV?.adapter = courseAdapter
        //item clicked
        courseGRV?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val i = Intent(requireContext(),ConvertingPage::class.java)
            i.putExtra("clicked",position)
            startActivity(i)
        }
    }
}