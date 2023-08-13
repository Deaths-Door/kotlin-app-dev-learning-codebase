package com.example.calculatormaybeworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_numeric_keypad.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Calculator(),"Calculator")
        adapter.addFragment(Convertor(),"Convertor")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
    fun pressed(view: View) {
        val a : Int = viewPager.currentItem
        Log.i("ViewPager",a.toString())
        currentEquation.text =  InputKeyPad(currentText = currentEquation.text.toString()).keypressed(view)
        if(currentEquation.text.toString() == "Invalid Equation"){
            //wait for 1sec
            Timer().schedule(1000){ currentEquation.hint = "0" }
            currentEquation.text = ""
        }
    }
    fun scientificKeypad(view : View){
        if ((view as Button).text.toString() == "OFF") {
            //change text
            view.text = "ON"
            //show keypad
            btnE.visibility = View.VISIBLE
            btnPie.visibility = View.VISIBLE
            btnSquare.visibility = View.VISIBLE
            btnSqrt.visibility = View.VISIBLE
            btnAns.visibility = View.VISIBLE
            tableRow1.visibility = View.VISIBLE
        } else {
            //change text
            view.text = "OFF"
            //hide keypad
            btnE.visibility = View.GONE
            btnPie.visibility = View.GONE
            btnSquare.visibility = View.GONE
            btnSqrt.visibility = View.GONE
            btnAns.visibility = View.GONE
            tableRow1.visibility = View.GONE
        }
    }
}