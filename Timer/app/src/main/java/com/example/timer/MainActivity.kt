package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null

    lateinit var secTextView: TextView
    lateinit var milliTextView: TextView
    lateinit var enter: Button
    lateinit var enterSec: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        secTextView = findViewById(R.id.secTextView)
        milliTextView = findViewById(R.id.milliTextView)
        enter = findViewById(R.id.enter)
        enterSec = findViewById(R.id.enterSec)

        enter.setOnClickListener {
            start()
        }

    }

    private fun start() {
        time = enterSec.text.toString().toInt() //사용자 입력으로 받은 값
        var tmp = time //임시 변수
        secTextView.text = time.toString() //초를 보여주는 textview를 사용자가 입력한 값으로 설정
        timerTask = timer(period = 10) {
            time++

            val sec = tmp - (time / 100)
            val mill = 99 - (time % 100)

            if (sec == 0 && mill == 0) {
                timerTask?.cancel()
            }

            runOnUiThread {
                secTextView.text = "$sec"
                milliTextView.text = "$mill"
            }

        }
    }
}