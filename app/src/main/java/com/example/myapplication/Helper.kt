package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Helper : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_helper)

        val bHelpBack = findViewById<Button>(R.id.bBack)

        bHelpBack.setOnClickListener {
            val SetAct = Intent(this, MainActivity::class.java)
            startActivity(SetAct)
            finish()
        }
    }
}