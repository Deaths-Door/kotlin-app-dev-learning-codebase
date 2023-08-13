package com.example.multifunctioncalculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.multifunctioncalculator.algebra.AlgebraOptions
import com.example.multifunctioncalculator.calculator.Calculator
import com.example.multifunctioncalculator.convertor.ConvertorOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCalculator.setOnClickListener {
            startActivity(Intent(this, Calculator::class.java))
        }
        btnConversion.setOnClickListener {
            startActivity(Intent(this, ConvertorOptions::class.java))
        }
        btnAlgrebra.setOnClickListener {
            startActivity(Intent(this, AlgebraOptions::class.java))
        }
    }
}