package com.example.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_task.*
import kotlinx.android.synthetic.main.item_todo.*
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

const val Db_Name = "todo.db"
class TaskActivity : AppCompatActivity(), View.OnClickListener {
    //the calender popup thingy
    //value of date,time etc
    private lateinit var myCalender : Calendar
    //interface for calender thingy
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    //interface for time thingy
    private lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener
    //labels
    private val labels = arrayListOf("Personal","Business","Shopping","Custom")
    var finalDate = 0L
    var finalTime = 0L
    //create db
    val db by lazy{
        AppDataBase.getDataBase(this).todoDao()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        dateEdit.setOnClickListener(this)
        timeEdit.setOnClickListener(this)
        btnSave.setOnClickListener(this)

        setUpSpinner()

    }
    private fun setUpSpinner() {
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,labels)
        labels.sort()
        spinnerCatogery.adapter = adapter
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.dateEdit ->{
                setCalenderListenser()
            }
            R.id.timeEdit -> {
                setTimeListener()
            }
            R.id.btnSave ->{
                runBlocking {
                    db.insertTask(
                        TodoModel(
                            titleInputLay.editText?.text.toString(),
                            taskInputLay.editText?.text.toString(),
                            spinnerCatogery.selectedItem.toString(),
                            dateEdit.text.toString().toLong(),
                            timeInputLay.editText?.text.toString().toLong(),
                            0))
                }
            }
        }
    }
    private fun setTimeListener() {
        //create object of myCalender
        myCalender = Calendar.getInstance()

        //update time values
        timeSetListener  = TimePickerDialog.OnTimeSetListener(){ _: TimePicker, hourOfDay: Int, min: Int ->
            myCalender.set(Calendar.HOUR_OF_DAY,hourOfDay)
            myCalender.set(Calendar.MINUTE,min)
            //update input layout when choose
            updateTime()
        }

        //open dialog to choose date
        val timePickerDialog =
            TimePickerDialog(this, timeSetListener,
                //true -> 24hr format or not
                myCalender.get(Calendar.HOUR_OF_DAY), myCalender.get(Calendar.MINUTE),true)

        //min date
        timePickerDialog.show()
    }
    private fun updateTime() {
        //format of time -> Mon,5 Jan 2020
        val myFormat = "h:mm"
        //if not using 24hr format
        //val myFormat = "h:mm a"
        //object of simple date format
        val sdf = SimpleDateFormat(myFormat)
        //setting text
        timeEdit.setText(sdf.format(myCalender.time))
    }
    private fun setCalenderListenser() {
        //create object of myCalender
        myCalender = Calendar.getInstance()

        //update time values
        dateSetListener  = DatePickerDialog.OnDateSetListener{ _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)

            //update input layout when choose
            updateDate()
        }

        //open dialog to choose date
        val datePickerDialog =
            DatePickerDialog(this, dateSetListener,
                myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH))

        //min date
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }
    private fun updateDate() {
        //format of time -> Mon,5 Jan 2020
        val myFormat = "EEE, d MMM yyyy"
        //object of simple date format
        val sdf = SimpleDateFormat(myFormat)
        //setting text
        dateEdit.setText(sdf.format(myCalender.time))
        timeInputLay.visibility = View.VISIBLE
    }
}