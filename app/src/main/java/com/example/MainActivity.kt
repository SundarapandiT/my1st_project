package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.bmicalculator.R
import com.example.bmicalculator.R.*
import com.example.bmicalculator.R.id.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)


        val getWeight = findViewById<EditText>(weight)
        val getHeight = findViewById<EditText>(height)
        val submitBtn = findViewById<Button>(button)
        val print = findViewById<TextView>(id.display)
        val button2: Button = findViewById(R.id.button2)


        submitBtn.setOnClickListener {
            val w = getWeight.text.toString()
            val h = getHeight.text.toString()
            if (w.isNotEmpty() && h.isNotEmpty()) {
                val weight = w.toFloat()
                val height = h.toFloat()/100
                val bmi = weight / (height * height)
                print.text = "Your BMI value:${bmi}\n\nYou are ${displays(bmi)}"
                button2.isEnabled = true
                button2.alpha = 1.0f
            }
            else
            {
                print.text="Please Enter weight and height!"
            }
        }
    }
}

fun displays(bmi: Float): String {
    return when {
        bmi < 18.5 -> "Underweight"
        bmi < 24.9 -> "Normal weight"
        bmi < 29.9 -> "Overweight"
        else -> "Obese"
    }
}
