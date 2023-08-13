package com.example.calculatormaybeworks

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_converting_page.*


class ConvertingPage : AppCompatActivity() {
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
    private var posClicked: Int = 0
    var lastClicked : Int = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converting_page)
        //pos clicked
        posClicked = intent.getIntExtra("PositionClicked",0)
        //drop down menus
        val arrayAdapter = ArrayAdapter(this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            conversionOptions[posClicked])
        autoCompleteTextView1.setAdapter(arrayAdapter)
        autoCompleteTextView2.setAdapter(arrayAdapter)
        autoCompleteTextView1.setSelection(arrayAdapter.getPosition(conversionOptions[posClicked][0]))
        autoCompleteTextView2.setSelection(arrayAdapter.getPosition(conversionOptions[posClicked][0]))

        //last tv clicked
        tv1.setOnClickListener{
            lastClicked = 1
            Toast.makeText(this,"TV1 CLICKED",Toast.LENGTH_SHORT).show()
        }
        tv2.setOnClickListener{
            lastClicked = 2
            Toast.makeText(this,"TV2 CLICKED",Toast.LENGTH_SHORT).show()
        }
    }
    fun pressed(view: View) {
        //input and convert
        if(lastClicked == 1 && autoCompleteTextView1.text.toString() != ""){
            InputKeyPad(currentText = tv1.text.toString()).keypressed(view)
            ConvertingMethods(posClicked,autoCompleteTextView1.text.toString(),autoCompleteTextView2.text.toString(),tv1.text.toString().toDouble()).convertingTypes()
        }
        else{
            InputKeyPad(currentText = tv2.text.toString()).keypressed(view)
            ConvertingMethods(posClicked,autoCompleteTextView2.text.toString(),autoCompleteTextView1.text.toString(),tv1.text.toString().toDouble()).convertingTypes()
        }
        Toast.makeText(this,"qwer",Toast.LENGTH_LONG).show()
    }
    fun scientificKeypad(view : View){
        Toast.makeText(this,"12312",Toast.LENGTH_LONG).show()
    }
}