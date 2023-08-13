package com.example.hardwaresensor

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get permissison
        val sensorManager:SensorManager? = getSystemService<SensorManager>()
        if(sensorManager == null){
            Toast.makeText(this,"CANT GET PERMISSON",Toast.LENGTH_SHORT).show()
            //finish activity
            finish()
        }
        else{
            //list of sensors in device
            val sensors:MutableList<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
            sensors.forEach(
                Log.i("HWSL","${it.name}")
            )
        }

    }
}