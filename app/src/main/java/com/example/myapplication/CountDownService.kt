package com.example.myapplication

import android.R
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.concurrent.thread


class CountDownService : Service() {


    val TimeWarn = Intent(this, TimerWarning2::class.java)
    var ScreenTimeSeconds : Int = 60
    var PauseTimeSeconds : Int = 30
    private val CHANNEL_ID = "my_channel_id"
    private val CHANNEL_NAME = "My Channel"
    private val NOTIFICATION_ID = 1

    var calendar = Calendar.getInstance()


    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Создание канала уведомлений (требуется для Android 8.0 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Построение уведомления с использованием NotificationCompat.Builder
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Отправка уведомления
        notificationManager.notify(NOTIFICATION_ID, builder.build())
        vibrate()
    }


    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (vibrator != null) {
            // Проверка наличия поддержки API для вибрации
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(0, 1000, 500, 1000),
                        -1
                    )
                )
            } else {
                vibrator.vibrate(longArrayOf(0, 1000, 500, 1000), -1)
            }
        }
    }






    private val handler = Handler()
    fun startApp(){
        thread{
            var hour = calendar.get(Calendar.HOUR_OF_DAY)
            var minute = calendar.get(Calendar.MINUTE)
            var second = calendar.get(Calendar.SECOND)

            var i: Int = ScreenTimeSeconds
            while (i > 0) {//вычисляем время, в котором будет заблокирован пользователь
                second += 1
                i -= 1
                if (second == 60) {
                    minute += 1
                    second = 0
                }
                if (minute == 60) {
                    hour += 1
                    minute = 0
                }
                if (hour == 24) {
                    hour = 0
                }
            }
            println("TIME, WHEN YOU WILL BE BLOCKED: $hour:$minute:$second")


            var calendar = Calendar.getInstance()

            var hourNow = calendar.get(Calendar.HOUR_OF_DAY)
            var minuteNow = calendar.get(Calendar.MINUTE)
            var secondNow = calendar.get(Calendar.SECOND)

            var a = secondNow
            var warnSec = second - 10
            var warnMin = minute
            if (warnSec < 0){
                warnSec += 60
                warnMin -= 1
            }



            while (true) {// первый таймер. Следит за экранным временем
                Thread.sleep(500)
                Log.d("", "TIME NOW: $hourNow:$minuteNow:$secondNow")
                if (hourNow >= hour && minuteNow >= minute && secondNow >= second) {//если время сейчас, достигло просчитанного времени
                    handler.post{
                        toastMeState("Turn off your phone,please")
                    }
                    break
                } else if (!isScreenOn(applicationContext)) {//если пользователь выключил экран
                    if (secondNow != a){
                        second += 1
                        if (second == 60){
                            second = 0
                            minute += 1
                        }
                        if (minute == 60){
                            minute = 60
                            hour += 1
                        }
                        if (hour == 24){
                            hour = 0
                        }
                        a = secondNow
                    }
                    Log.d("", "TIME, WHEN YOU WILL BE BLOCKED: $hour:$minute:$second")
                }
                if (warnSec == secondNow && warnMin == minuteNow){
                    handler.post{
                        toastMeState("Remaining 10 sec!")
                    }
                }
                calendar = Calendar.getInstance()

                hourNow = calendar.get(Calendar.HOUR_OF_DAY)
                minuteNow = calendar.get(Calendar.MINUTE)
                secondNow = calendar.get(Calendar.SECOND)

            }
            println("YES, FIRST THREAD WAS FINISHED SUCCESSFUL")

            handler.post {
                showNotification("Extra time","You have 30 sec extra time to finish")
            }
            secondNow = calendar.get(Calendar.SECOND)
            var ExtraSec = secondNow + 30
            if(ExtraSec >= 60){
                ExtraSec = ExtraSec - 60
            }
            while (secondNow != ExtraSec){
                Thread.sleep(500)
                Log.d("","Extra time: ")
                Log.d("",ExtraSec.toString())
                Log.d("","SecondNOW: ")
                Log.d("",secondNow.toString())
                if (!isScreenOn(applicationContext)){
                    break
                }
                calendar = Calendar.getInstance()
                secondNow = calendar.get(Calendar.SECOND)
            }

            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)
            second = calendar.get(Calendar.SECOND)//опять узнаем нынешнее время

            var j = PauseTimeSeconds
            while (j > 0) {//просчитываем время, в котором будет разблокировка
                second += 1
                j -= 1
                if (second == 60) {
                    minute += 1
                    second = 0
                }
                if (minute == 60) {
                    hour += 1
                    minute = 0
                }
                if (hour == 24) {
                    hour = 0
                }
            }


            while (true) {// второй таймер, следит за отдыхом
                Thread.sleep(500)
                Log.d("", "TIME NOW: $hourNow:$minuteNow:$secondNow")
                if (hourNow >= hour && minuteNow >= minute && secondNow >= second) {//если время сейчас, достигло просчитанного времени
                    break
                } else if (isScreenOn(applicationContext)) {//если пользователь выключил экран
                    if (secondNow != a){
                        second += 1
                        if (second == 60){
                            second = 0
                            minute += 1
                        }
                        if (minute == 60){
                            minute = 60
                            hour += 1
                        }
                        if (hour == 24){
                            hour = 0
                        }
                        a = secondNow
                    }
                    handler.post{
                        toastMeState("Turn off your phone,please")
                    }
                    Log.d("", "TIME, WHEN YOU WILL BE UNBLOCKED: $hour:$minute:$second")
                }
                calendar = Calendar.getInstance()

                hourNow = calendar.get(Calendar.HOUR_OF_DAY)
                minuteNow = calendar.get(Calendar.MINUTE)
                secondNow = calendar.get(Calendar.SECOND)
            }
            handler.post {
                showNotification("How was your rest?","The lockdown has been lifted, you can continue your work!")
            }
        }
    }




    fun isScreenOn(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager?
        return powerManager?.isInteractive == true
    }

    private fun toastMeState(message: String) {
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startApp()
        return START_STICKY
    }



    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    fun isActivityRunning(activityClass: Class<*>): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningActivities = activityManager.getRunningTasks(1)

        if (runningActivities.isNotEmpty()) {
            val topActivity = runningActivities[0].topActivity
            return topActivity?.className == activityClass.name
        }

        return false
    }



}
