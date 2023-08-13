package com.example.convertor

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_convertor.*
import kotlinx.android.synthetic.main.numeric_keypad.*


class Convertor : AppCompatActivity(),View.OnClickListener {
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
    //for conversion , default values
    private var posClicked : Int = 0
    private var selected1 = ""
    private var selected2 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convertor)
        //pos clicked
        posClicked = intent.getIntExtra("PositionClicked",0)
        val a = autoCompleteTextView1.text.toString()
        Log.i("Test",a)
        //drop down menus
        val arrayAdapter = ArrayAdapter(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            conversionOptions[posClicked])
        autoCompleteTextView1.setAdapter(arrayAdapter)
        autoCompleteTextView2.setAdapter(arrayAdapter)
        //selecting
        autoCompleteTextView1.setOnItemClickListener { _, _, position, _ ->
            selected1 = conversionOptions[posClicked][position]
        }
        autoCompleteTextView2.setOnItemClickListener { _, _, position, _ ->
            selected2 = conversionOptions[posClicked][position]
        }

        //last tv clicked
        tv1.setOnClickListener{
            lastclicked = 1
            Toast.makeText(this,"TV1 CLICKED",Toast.LENGTH_SHORT).show()
        }
        tv2.setOnClickListener{
            lastclicked = 2
            Toast.makeText(this,"TV2 CLICKED",Toast.LENGTH_SHORT).show()
        }
    }
    //string builder
    private fun String.deleteAt(x: Int) = StringBuilder(this).deleteAt(x)
    //numeric keypad
    private var lastclicked = 1
    override fun onClick(view: View?) {
        when((view as Button).text.toString()){
            "C"  -> {
                if(lastclicked == 1){
                    tv1.text = ""
                } else{
                    tv2.text = ""
                }
            }
            "⌫" -> {
                if(lastclicked == 1 && tv1.text.toString() != ""){
                    tv1.text = tv1.text.toString().deleteAt(tv1.text.toString().length - 1)
                }
                else if(lastclicked == 2 && tv2.text.toString() != ""){
                    tv2.text = tv2.text.toString().deleteAt(tv2.text.toString().length - 1)
                }
            }
            else -> {
                if(lastclicked == 1){
                    tv1.text = tv1.text.toString() + (view as Button).text
                }
                else{
                    tv2.text = tv2.text.toString() + (view as Button).text
                }
            }
        }
        //Convert from 1 -> 2 or 2 -> 1
        if(lastclicked == 1 && tv1.text != "" && selected1 != "" && selected2 != ""){
            tv2.text = ConvertingMethods(posClicked,selected1,selected2,tv1.text.toString().toDouble()).convertingTypes()
        }
        else if(lastclicked == 2 && tv2.text != "" && selected1 != "" && selected2 != ""){
            tv1.text = ConvertingMethods(posClicked,selected2,selected1,tv2.text.toString().toDouble()).convertingTypes()
        }
    }
}