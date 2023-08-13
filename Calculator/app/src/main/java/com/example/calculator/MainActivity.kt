package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.numeric_keypad.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(){
    //recycle view
    private val history: ArrayList<History> = ArrayList()
    private lateinit var historyAdapter : HistoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        specialFunctions()

        //recycle view
        rvHistory.layoutManager = LinearLayoutManager(this)
        historyAdapter = HistoryAdapter(history)
        rvHistory.adapter = historyAdapter
    }
    //string builder
    private fun String.deleteAt(x: Int) = StringBuilder(this).deleteAt(x)
    private fun String.insert(index : Int, string : String): String = (this).insert(index,string)
    //currentEquation.text.toString(

    //solve
    private fun solve(x:String) : String {
        return try{
            (ExpressionBuilder(x).build().evaluate()).toString()
        } catch (e : Exception){
            "Invalid Equation"
        }
    }
    //TODO(CRASHES APP FOR GOD KNOWS Y)
    //preview ans
    /*private fun previewAns(){
        if(solve(toShowEquation()) != "Invalid Equation"){
            currentEquationAns.text = intOrDecimal((toShowEquation().toDouble()))
        }
    }*/
    private fun toShowEquation() : String = currentEquation.text.toString()
    //numeric keypad and other possible keys
    fun numberPressed(view: View) {
        val x = (view as Button).text.toString()
        when(x){
            "x²" -> currentEquation.text = toShowEquation() + "²"
            else -> currentEquation.text = toShowEquation() + x
        }
    }
    //on off
    fun scientificKeypad(view: View) {
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
    //answer
    private var result : String = "0"
    private fun specialFunctions() {
        btnEqual.setOnClickListener {
            // replace somethings
            val toSolveEquation = toShowEquation()
                .replace("×","*").replace("÷","/")
                .replace("²","^2").replace("√","sqrt")
                .replace("Ans",result)

            //answer
            var result : String = ""
            when(solve((toSolveEquation))){
                "Invalid Equation" -> {
                    //invalid equation
                    currentEquation.text = ""
                    currentEquation.hint = "Invalid Equation"
                    //wait for 1sec
                    Timer().schedule(1000){ currentEquation.hint = "0" }
                }
                else ->{
                    result = intOrDecimal(solve(toSolveEquation).toDouble())
                }
            }
            // ans = "1234" ->  " =1234"
            //adapter
            history.add(History(toShowEquation(),result))
            historyAdapter.notifyItemInserted(history.size - 1)
            //ans as text
            currentEquation.text = result
        }
        btnClear.setOnClickListener {
           /* C or AC
            C clears current number
            AC clears recycle view */
            if(toShowEquation() == ""){
                btnClear.text = "AC"
                history.clear()
                historyAdapter.notifyDataSetChanged()
            }else{
                btnClear.text = "C"
                currentEquation.text = ""
            }

        }
        btnDel.setOnClickListener {
            if(toShowEquation() != ""){
                currentEquation.text = toShowEquation().deleteAt(toShowEquation().length - 1)
            }
        }
        btnPercent.setOnClickListener{}
        btnDecToFrac.setOnClickListener {
            try{
                currentEquation.text = decimalToFraction(toShowEquation().toDouble())
            }
            catch (e : Exception){}
        }
       //btnFactorial.setOnClickListener{}
    }
    //int or decimal
    private fun intOrDecimal(x: Double): String {
        return if(abs(x %1) > 0 && abs(x %1) < 1){
            //decimal needed
            x.toString()
        } else{
            //integer
            x.toInt().toString()
        }
    }
    //decimal to fraction
    private fun decimalToFraction(x : Double): String{
        try{
            if (x < 0) {
                return "-" + decimalToFraction(-x)
            }
            val tolerance = 1.0E-6
            var h1 = 1.0
            var h2 = 0.0
            var k1 = 0.0
            var k2 = 1.0
            var b = x
            do {
                val a = floor(b)
                var aux = h1
                h1 = a * h1 + h2
                h2 = aux
                aux = k1
                k1 = a * k1 + k2
                k2 = aux
                b = 1 / (b - a)
            } while (abs(x - h1 / k1) > x * tolerance)
            //double to int
            val numerator = h1.roundToInt()
            val denominator = k1.roundToInt()
            //if 13/1 -> 13
            if(denominator == 1){
                return "$numerator"
            }
            return "$numerator/$denominator"
        }
        catch (e : Exception){
            return x.toString()
        }
    }
}