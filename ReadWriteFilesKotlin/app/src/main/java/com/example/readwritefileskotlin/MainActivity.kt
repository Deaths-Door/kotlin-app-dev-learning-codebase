package com.example.readwritefileskotlin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //read and write files
        btnWrite.setOnClickListener{
            val dataDir = ContextCompat.getDataDir(this)
            val myFile = File(dataDir,"file.txt")
            myFile.writeText(editTextTextPersonName.text.toString())
        }
        //only for small files
        btnRead.setOnClickListener{
            val dataDir = ContextCompat.getDataDir(this)
            val myFile = File(dataDir,"file.txt")
            textView.text = myFile.readText()
        }

        //preferences save tiny data
        val sPhef = getPreferences(Context.MODE_PRIVATE)
        //run app first time no perferences
        val color = sPhef.getInt("Color",Color.WHITE)
        main.setBackgroundColor(color)

       // var editor = sPhef.edit()
        //put values
       // editor.putInt("Colour", Color.RED)
      //  editor.apply()
        //get values
      //  sPhef.getInt("Colour")

        fun saveColour(color :Int) {
            val editor = sPhef.edit()
            editor.putInt("Color",color)
            editor.apply()
        }
        //seting backgorund
        btnBlue.setOnClickListener{
            main.setBackgroundColor(Color.BLUE)
            saveColour(Color.BLUE)
        }
        btnRed.setOnClickListener{
            main.setBackgroundColor(Color.RED)
            saveColour(Color.RED)
        }
        btnGreen.setOnClickListener{
            main.setBackgroundColor(Color.GREEN)
            saveColour(Color.GREEN)
        }

    }
}