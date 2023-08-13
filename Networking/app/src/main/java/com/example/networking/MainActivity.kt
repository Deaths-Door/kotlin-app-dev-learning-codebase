package com.example.networking

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.networking.databinding.MainActivityBinding
import com.example.networking.databinding.MainActivityBinding.inflate
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        binding.text = "Test"
        binding.text = "WORKS"
        setContentView(binding.root)
        binding.button.setOnClickListener {
            updateTextView()
        }

    }
    fun updateTextView(){
        val netWorkTask : NetWorkTask = NetWorkTask()
        netWorkTask.execute("https://www.google.com")
    }
    class NetWorkTask: AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg s1: String?): String{
            val stringUrl = s1[0]
            //url object
            val url:URL = URL(stringUrl)
            val httpUrlConnection:HttpURLConnection = url.openConnection() as HttpURLConnection
            //input stream
            val inputStream : InputStream = httpUrlConnection.inputStream
            val scanner: Scanner = Scanner(inputStream)
            //read entire input at once
            scanner.useDelimiter("\\A")
            if(scanner.hasNext()) {
                return scanner.next()
            }
            return "Failed to Load"
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            MainActivity().binding.text = result
        }
    }
}