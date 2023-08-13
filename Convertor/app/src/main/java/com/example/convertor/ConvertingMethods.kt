package com.example.convertor

import kotlin.math.round
import kotlin.math.abs
import kotlin.math.pow

class ConvertingMethods(private val type : Int, private val from :String, private val to :String, var num : Double){
    fun convertingTypes(): String {
        when(type){
            0 -> length()
            1 -> time()
            2 -> mass()
            3 -> area()
            4 -> volume()
            5 -> speed()
            6 -> temperature()
            7 -> numeralSystem()
            8 -> pressure()
        }
        return intOrDecimal(num)
    }
    //int or decimal
    private fun intOrDecimal(x: Double): String {
        return if(abs(x %1) > 0 && abs(x %1) < 1){
            //decimal needed
            x.round(5).toString()
        } else{
            //integer
            x.toInt().toString()
        }
    }
    //round to 5 decimal places
    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
    //values
    private fun length() {
        //standard is meters
        when(from){
            "km" -> num *= 1000
            "cm" -> num /= 100
            "mm" -> num /= 1000
        }
        when(to){
            "km" -> num /= 1000
            "cm" -> num *= 100
            "mm" -> num *= 1000
        }
    }
    private fun time() {
        //standard is min
        when(from){
            // 365 * 24 * 60
            "year" -> num *= 525600
            // 7 * 24 * 60
            "week" -> num *= 10080
            // 24 * 60
            "day"  -> num *= 1440
            "hr"   -> num *= 60
            "sec"  -> num  /= 60
            "ms"   -> num  /= 60000
            "μs"   -> num  /= 6e+7
        }
        when(to){
            "year" -> num  /= 525600
            "week" -> num  /= 10080
            "day"  -> num  /= 1440
            "hr"   -> num  /= 60
            "sec"  -> num *= 60
            "ms"   -> num *= 60000
            "μs"   -> num  *= 6e+7
        }
    }
    private fun mass() {
        //standard is kg
        when(from){
            "tonne" -> num *= 1000
            "g"     -> num /= 1000
            "mg"    -> num /= 1e+6
            "μg"    -> num /= 1e+9
            "ng"    -> num /= 1e-12
        }
        when(to){
            "tonne" -> num /= 1000
            "g"     -> num *= 1000
            "mg"    -> num *= 1e+6
            "μg"    -> num *= 1e+9
            "ng"    -> num *= 1e-12
        }
    }
    private fun area() {
       when(from){
           "km²" -> num *= 1000.0.pow(2)
           "cm²" -> num /= 100.0.pow(2)
           "mm²" -> num /= 1000.0.pow(2)
       }
       when(to){
           "km²" -> num /= 1000.0.pow(2)
           "cm²" -> num *= 100.0.pow(2)
           "mm²" -> num *= 1000.0.pow(2)
       }
    }
    //TODO(NOT WORKING)
    private fun volume() {
        when(from){
            "km³" -> num *= 1000.0.pow(3)
            "cm³" -> num *= 100.0.pow(3)
            "mm³" -> num *= (1000.0.pow(3))
        }
        when(from){
            "km³" -> num *= (1000.0.pow(3))
            "cm³" -> num *= (100.0.pow(3))
            "mm³" -> num *= (1000.0.pow(3))
        }
    }
    private fun speed() {
        TODO("Not yet implemented")
    }
    private fun temperature() {
        //standard is C
        when(from){
            "F" -> num = (num - 32) * 5/9
            "K" -> num -= 273.15
        }
        when(to){
            "F" -> num = (num * 9/5) + 32
            "K" -> num += 273.15
        }
    }
    private fun numeralSystem() {
        TODO("Not yet implemented")
    }
    private fun pressure() {
        TODO("Not yet implemented")
    }
}