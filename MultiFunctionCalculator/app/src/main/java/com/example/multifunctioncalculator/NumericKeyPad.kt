package com.example.multifunctioncalculator

import android.widget.Button
import org.mariuszgromada.math.mxparser.Expression
import kotlin.math.abs

/**
 * Ruleset
 *
 * 1 -> Calculator
 *
 * 2 -> Conversion
 *
 * **/
class NumericKeyPad(val ruleSet: Int, var view: Any,var equation: String) {
    private val s1 = (view as Button).text.toString()

    //string builder
    private fun String.deleteAt(x: Int) = StringBuilder(this).deleteAt(x).toString()

    fun rule():String{
        pressed()
        return equation
    }

    private fun pressed() {
        when((view as Button).text){
            "OFF" -> {}
            "ON"  -> {}
            "⌫" -> {
                if(equation != ""){
                    equation = equation.deleteAt(equation.length - 1)
                }
            }
            "=" -> {
                solve()
            }
            "x²" -> add("²")
            else -> add(s1)
        }
    }
    private fun add(s: String) {
        equation += s
    }
    //TODO(INT OR DECIMAL NOT WORKING)
    private fun solve() {
        val solveEquation = equation
            .replace("×","*").replace("÷","/")
            .replace("²","^2").replace("√","sqrt")
        //solve
       /* equation = try{
            intOrDecimal(Expression(solveEquation).calculate().toString().toDouble())
        }catch (e : Exception){
            "Invalid Equation"
        }
        equation = equation.toDouble().round(5).toString()*/
        equation = try{
            intOrDecimal(Expression(solveEquation).calculate().toString().toDouble().round(5))
        }catch (e : Exception){
            "Invalid Equation"
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
    //round to 5 decimal places
    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}