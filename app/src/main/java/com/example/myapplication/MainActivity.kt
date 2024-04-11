package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private fun toastMeState(message: String) {
        Toast.makeText(this, "${lifecycle.currentState}, $message", Toast.LENGTH_LONG).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //функционал кнопки Настройки
        val bSettings = findViewById<Button>(R.id.bSettings)
        bSettings.setOnClickListener {
            val SetAct = Intent(this, SettingsActivity::class.java)
            startActivity(SetAct)
            finish()
        }


        //функционал кнопки Статистики
        val bHelp = findViewById<Button>(R.id.bHelp)
        bHelp.setOnClickListener {
            val SetAct3 = Intent(this, Helper::class.java)
            startActivity(SetAct3)
            finish()
        }


    }


}