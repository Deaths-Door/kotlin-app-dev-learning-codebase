package com.example.calculator30.fragments.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.calculator30.MainActivity
import com.example.calculator30.R
import com.example.calculator30.databinding.FragmentConvertorBinding
import com.example.calculator30.viewmodel.HistoryViewModel
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_convertor.*

class Convertor : Fragment() {
    //converting options list
    private val options = arrayListOf("Length","Time","Mass","Area","Volume","Speed",
        "Temperature","Numeral System","Pressure")
    //units
    private val conversionOptions = arrayListOf(
        arrayListOf("km","m","cm","mm"),
        arrayListOf("year","week","day","hr","min","sec","ms","μs"),
        arrayListOf("tonne","kg","g","mg","μg","ng"),
        arrayListOf("km²","m²","cm²","mm²"),
        arrayListOf("km³","m³","cm³","mm³"),
        arrayListOf(""),
        arrayListOf("C","F","K"),
        arrayListOf(""),
        arrayListOf("")
    )
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View = inflater.inflate(R.layout.fragment_convertor,container,false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //last clicked textView
        tv1.setOnClickListener { HistoryViewModel().lastClicked = 1 }
        tv2.setOnClickListener { HistoryViewModel().lastClicked = 2 }
    }
}