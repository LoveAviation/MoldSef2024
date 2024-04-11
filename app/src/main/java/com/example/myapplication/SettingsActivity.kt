package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast


class SettingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        val stopServiceIntent = Intent(this, CountDownService::class.java)
        val intent = Intent(this, CountDownService::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        var pref : SharedPreferences
        pref = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = pref.edit()

        val bSettingsBack = findViewById<Button>(R.id.bBack)
        val sparkplug = findViewById<Button>(R.id.sparkPlug)

        val sbScreen = findViewById<SeekBar>(R.id.seekBarScreen)
        val sbWarning= findViewById<SeekBar>(R.id.seekBarWarning)
        val sbPause = findViewById<SeekBar>(R.id.seekBarPause)
        val textST = findViewById<TextView>(R.id.textST)
        val textTW = findViewById<TextView>(R.id.textTW)
        val textPT = findViewById<TextView>(R.id.textPT)













        bSettingsBack.setOnClickListener {
            val SetAct = Intent(this, MainActivity::class.java)
            startActivity(SetAct)
            finish()
        }

        sparkplug.setOnClickListener {
            startService(Intent(this, CountDownService::class.java))
            toastMeState("Started succesfully!")
            finish()
        }

        var SavedProgres : Int = pref.getInt("sbScreen",0)!!
        sbScreen.progress = SavedProgres
        when(pref.getInt("sbScreen",0)!!){
            0 -> textST.text = "\n Screen time: 1 min(test)"
            1 -> textST.text = "\n Screen time: ONLY TEST"
            2 -> textST.text = "\n Screen time: ONLY TEST"
            3 -> textST.text = "\n Screen time: ONLY TEST"
            4 -> textST.text = "\n Screen time: ONLY TEST"
        }
        sbScreen.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> textST.text = "\n Screen time: 1 min(test)"
                    1 -> textST.text = "\n Screen time: ONLY TEST"
                    2 -> textST.text = "\n Screen time: ONLY TEST"
                    3 -> textST.text = "\n Screen time: ONLY TEST"
                    4 -> textST.text = "\n Screen time: ONLY TEST"
                }
                editor.putInt("sbScreen", progress)
                editor.apply()
                stopService(stopServiceIntent)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })




        SavedProgres = pref.getInt("sbWarning",0)!!
        sbWarning.progress = SavedProgres
        when(pref.getInt("sbWarning",0)!!){
            0 -> textTW.text = "\n Time to warning: 10 sec(test)"
            1 -> textTW.text = "\n Time to warning: ONLY TEST"
            2 -> textTW.text = "\n Time to warning: ONLY TEST"
            3 -> textTW.text = "\n Time to warning: ONLY TEST"
            4 -> textTW.text = "\n Time to warning: ONLY TEST"
        }
        sbWarning.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> textTW.text = "\n Time to warning: 10 sec(test)"
                    1 -> textTW.text = "\n Time to warning: ONLY TEST"
                    2 -> textTW.text = "\n Time to warning: ONLY TEST"
                    3 -> textTW.text = "\n Time to warning: ONLY TEST"
                    4 -> textTW.text = "\n Time to warning: ONLY TEST"
                }
                intent.putExtra("TimeToWarning", pref.getInt("sbWarning", 1*60*1000))
                editor.apply()
                stopService(stopServiceIntent)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })


        SavedProgres = pref.getInt("sbPause",0)!!
        sbPause.progress = SavedProgres
        when(pref.getInt("sbPause",0)!!){
            0 -> textPT.text = "\n Pause time: 30 sec(test)"
            1 -> textPT.text = "\n Pause time: ONLY TEST"
            2 -> textPT.text = "\n Pause time: ONLY TEST"
            3 -> textPT.text = "\n Pause time: ONLY TEST"
            4 -> textPT.text = "\n Pause time: ONLY TEST"
        }
        sbPause.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                when(progress){
                    0 -> textPT.text = "\n Pause time: 30 sec(test)"
                    1 -> textPT.text = "\n Pause time: ONLY TEST"
                    2 -> textPT.text = "\n Pause time: ONLY TEST"
                    3 -> textPT.text = "\n Pause time: ONLY TEST"
                    4 -> textPT.text = "\n Pause time: ONLY TEST"
                }
                editor.putInt("sbPause", progress)
                intent.putExtra("PauseTime", pref.getInt("sbPause", 3*60*1000))
                editor.apply()
                stopService(stopServiceIntent)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

    }



    private fun toastMeState(message: String) {
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
    }


}