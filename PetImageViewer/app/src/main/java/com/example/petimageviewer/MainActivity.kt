package com.example.petimageviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var start: CheckBox = findViewById(R.id.startCB)
        var q1: TextView = findViewById(R.id.q1)
        var rg1: RadioGroup = findViewById(R.id.rg1)
        var finish: Button = findViewById(R.id.finishbt)
        var img: ImageView = findViewById(R.id.imgPet)

        start.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                q1.visibility = android.view.View.VISIBLE
                rg1.visibility = android.view.View.VISIBLE
                finish.visibility = android.view.View.VISIBLE
            } else {
                q1.visibility = android.view.View.INVISIBLE
                rg1.visibility = android.view.View.INVISIBLE
                finish.visibility = android.view.View.INVISIBLE
                img.visibility=android.view.View.INVISIBLE
            }
        }

        finish.setOnClickListener {
            when (rg1.checkedRadioButtonId) {
                R.id.dog -> img.setImageResource(R.drawable.dog)
                R.id.cat -> img.setImageResource(R.drawable.cat)
                R.id.rabbit -> img.setImageResource(R.drawable.rabbit)
            }
            img.visibility=android.view.View.VISIBLE
        }
    }
}