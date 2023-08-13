package com.example.multifunctioncalculator.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multifunctioncalculator.History
import com.example.multifunctioncalculator.NumericKeyPad
import com.example.multifunctioncalculator.R
import kotlinx.android.synthetic.main.activity_calculator.*
import kotlinx.android.synthetic.main.numeric_keypad.*
import java.util.ArrayList


class Calculator : AppCompatActivity() {
    //recycle view
    private val history: ArrayList<History> = ArrayList()
    private lateinit var historyAdapter : HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        //recycle view
        rvHistory.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(history)
        rvHistory.adapter = historyAdapter
    }

    private fun toShowEquation(): String = currentEquation.text.toString()
    fun numericKeyPadPressed(view: View) {
        when((view as Button).text){
            "OFF" ->{
                //change text
                view.text = "ON"
                //show keypad
                btnE.visibility = View.VISIBLE
                btnPie.visibility = View.VISIBLE
                btnSquare.visibility = View.VISIBLE
                btnSqrt.visibility = View.VISIBLE
                btnAns.visibility = View.VISIBLE
                tableRow1.visibility = View.VISIBLE
            }
            "ON" ->{
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
            else ->  currentEquation.text = NumericKeyPad(1, view, toShowEquation()).rule()
        }
    }
}
