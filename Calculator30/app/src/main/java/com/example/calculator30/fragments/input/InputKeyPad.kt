package com.example.calculator30.fragments.input

import android.view.View
import android.widget.Button
import org.mariuszgromada.math.mxparser.Expression

class InputKeyPad(var currentText: String = "", var result: String = "") {
    private val words = arrayListOf("tan(", "cos(", "sin(")
    private fun String.deleteAt(x: Int) = StringBuilder(this).deleteAt(x)
    fun keypressed(view : View): String {
        val x = (view as Button).text.toString()
        when (x) {
            "x²" -> currentText += "²"
            "=" ->  result = solve(currentText)
            "⌫" -> {
                try {
                    if (currentText != "") {
                        //if tan ,sin etc not remove full word
                        currentText = if (currentText.indexOfAny(words) == currentText.length - 4) {
                            currentText.replaceRange(
                                currentText.length - 4,
                                currentText.length,
                                ""
                            )
                        } else {
                            currentText.deleteAt(currentText.length - 1).toString()
                        }
                    }
                } catch (e: Exception) {
                }
            }
            "C" -> currentText = ""
            "tan" -> currentText += "$x("
            "cos" -> currentText += "$x("
            "sin" -> currentText += "$x("
            else -> currentText += x
        }
        return currentText
    }
    fun solve(equation:String): String {
        var toSolveEquation = equation
        //unclosed brackets
        val openBracCount = toSolveEquation.count { it == '(' }
        val closeBracCount = toSolveEquation.count { it == ')' }
        if (openBracCount != closeBracCount) {
            repeat(openBracCount - closeBracCount) {
                toSolveEquation += ")"
            }
        }
        return try {
            //solve
            val solvedEquation = Expression(toSolveEquation).calculate().toString()
            solvedEquation
        } catch (e: Exception) {
            "Invalid Equation"
        }
    }
}