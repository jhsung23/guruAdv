package com.example.basiccalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var input1: EditText = findViewById(R.id.edit1)
        var input2: EditText = findViewById(R.id.edit2)

        var plus: Button = findViewById(R.id.plusbutton)
        var minus: Button = findViewById(R.id.minusbutton)
        var multiply: Button = findViewById(R.id.multiplybutton)
        var divide: Button = findViewById(R.id.dividebutton)

        var result: TextView = findViewById(R.id.resultTextview)

        plus.setOnClickListener {
            var res = input1.text.toString().toInt() + input2.text.toString().toInt()
            result.setText(res.toString())
        }
        minus.setOnClickListener {
            var res = input1.text.toString().toInt() - input2.text.toString().toInt()
            result.setText(res.toString())
        }
        multiply.setOnClickListener {
            var res = input1.text.toString().toInt() * input2.text.toString().toInt()
            result.setText(res.toString())
        }
        divide.setOnClickListener {
            var res = input1.text.toString().toInt() / input2.text.toString().toInt()
            result.setText(res.toString())
        }
    }
}