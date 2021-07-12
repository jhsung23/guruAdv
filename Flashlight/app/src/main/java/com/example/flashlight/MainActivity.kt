package com.example.flashlight

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

class MainActivity : AppCompatActivity() {
    lateinit var flashSwitch: Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashSwitch = findViewById(R.id.flashSwitch)
        val torch = Torch(this)
        val intent = Intent(this, TorchService::class.java)

        flashSwitch.setOnCheckedChangeListener { buttonView, ischecked ->
            if (ischecked) {
                intent.action = "on"
                startService(intent)
            } else {
                intent.action = "off"
                startService(intent)
            }

        }
    }
}