package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class TimerWarning2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_warning)

        val bHelpBack = findViewById<Button>(R.id.bBack)

        bHelpBack.setOnClickListener {
            val SetAct = Intent(this, MainActivity::class.java)
            startActivity(SetAct)
            finish()
        }
    }
}