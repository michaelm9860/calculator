package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    
    private var tvInput: TextView? = null

    private var lastDot = false

    private var lastNumeric = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onCLR(view: View){
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View){
        if (lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        }else{
            value.contains("/")
                    || value.contains("+")
                    || value.contains("*")
                    || value.contains("-")
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    var splitValue = tvValue.split("-")

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()){
                        a = prefix + a
                    }

                    tvInput?.text = removeDotZero((a.toDouble() - b.toDouble()).toString())


                }else if(tvValue.contains("+")){
                    var splitValue = tvValue.split(("+"))

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()){
                        a = prefix + a
                    }

                    tvInput?.text = removeDotZero((a.toDouble() + b.toDouble()).toString())
                }else if(tvValue.contains("/")){
                    var splitValue = tvValue.split(("/"))

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()){
                        a = prefix + a
                    }

                    tvInput?.text = removeDotZero((a.toDouble() / b.toDouble()).toString())

                }else if(tvValue.contains("*")){
                    var splitValue = tvValue.split(("*"))

                    var a = splitValue[0]
                    var b = splitValue[1]

                    if (prefix.isNotEmpty()){
                        a = prefix + a
                    }

                    tvInput?.text = removeDotZero((a.toDouble() * b.toDouble()).toString())

                }



            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeDotZero(result: String): String{
        var value = result
        if (result.contains(".") && result.endsWith("0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

}