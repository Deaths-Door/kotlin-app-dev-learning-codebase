package com.example.calculatormaybeworks

import android.view.View
import android.widget.Button
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class InputKeyPad(var currentText : String = "",var result : String = ""){
    private fun String.deleteAt(x: Int) = StringBuilder(this).deleteAt(x)
    fun keypressed(view : View): String{
        val x = (view as Button).text.toString()
        when(x){
            "x²" -> currentText += "²"
            "=" -> solve()
            "⌫" ->if(currentText != "") currentText = currentText.deleteAt(currentText.length - 1).toString()
            "C" -> currentText = ""
            "D-F" -> currentText = decimalToFraction(currentText.toDouble())
            else -> currentText += x

        }
        return currentText
    }

    private fun decimalToFraction(x : Double): String {
        //decimal to frac
        if(x.toString().indexOf("/") == null){
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
            catch (e : Exception){ }
        }
        //frac to decimal
        return solve().toString()
    }
    private fun solve(){
        val toSolveEquation = currentText
            .replace("×","*").replace("÷","/")
            .replace("²","^2").replace("√","sqrt")
            .replace("Ans",result)
        try{
            val e = Expression(currentText)
            val ans = e.calculate().toString()
            result = intOrDecimal(Expression(currentText).calculate())
             currentText = result
        }catch (e : Exception){
            currentText = "Invalid Equation"
        }
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
}