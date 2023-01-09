package com.simo26.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.time.Duration

class MainActivity : AppCompatActivity() {

    var myCountDownTimer: CountDownTimer? = null
    val START_TIME_IN_MILI_SECONDS: Long = 25 * 60 * 1000
    var remainingTime: Long = START_TIME_IN_MILI_SECONDS
    var isTimerWorking = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener {
            if (!isTimerWorking) {
                startTimer()
                title_tv.text = resources.getText(R.string.keep_going)
            }
        }
        reset_button.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        myCountDownTimer = object : CountDownTimer(START_TIME_IN_MILI_SECONDS, 1000) {
            var i : Long = 0
            val numOfTimes = START_TIME_IN_MILI_SECONDS / 1000
            override fun onTick(restDuration: Long) {
                i++
                remainingTime = restDuration
                updateTimerText()
                val barPercentage = ((remainingTime.toDouble() / START_TIME_IN_MILI_SECONDS.toDouble()) * 100).toInt()
                progressBar.progress = barPercentage
                if ( i == numOfTimes ){
                    progressBar.progress = 0
                }
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Finished Duration!!!", Toast.LENGTH_LONG).show()
                isTimerWorking = false
            }

        }.start()
        isTimerWorking = true
    }

    private fun resetTimer() {
        myCountDownTimer?.cancel()
        remainingTime = START_TIME_IN_MILI_SECONDS
        updateTimerText()
        title_tv.text = resources.getText(R.string.take_pomodoro)
        isTimerWorking = false
        progressBar.progress = 100
    }

    private fun updateTimerText() {
        val minutes = remainingTime / 1000 / 60
        val seconds = (remainingTime / 1000) % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timer_tv.text = formattedTime
    }
}