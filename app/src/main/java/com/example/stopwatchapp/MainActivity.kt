package com.example.stopwatchapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var isRunning = false
    var timer : Timer? = null
    var time = 0
    private lateinit var start_btn: Button
    private lateinit var refresh_btn: Button
    private lateinit var minute_tv:TextView
    private lateinit var second_tv: TextView
    private lateinit var millisecond_tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn = findViewById(R.id.start_btn)
        refresh_btn = findViewById(R.id.refresh_btn)
        minute_tv = findViewById(R.id.minute_tv)
        second_tv = findViewById(R.id.second_tv)
        millisecond_tv = findViewById(R.id.millisecond_tv)

        start_btn.setOnClickListener(this)
        refresh_btn.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.start_btn -> {
                if (isRunning) {
                    pause()
                } else {
                    start()
                }
            }
            R.id.refresh_btn -> {
                refresh()
            }
        }
    }

    private fun start() {
        start_btn.text = "일시정지"
        start_btn.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 10) {
            time++

            val milli_second = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {
                if (isRunning) {
                    millisecond_tv.text =
                        if (milli_second < 10)
                        ".0${milli_second}"
                        else
                        ".${milli_second}"
                    second_tv.text =
                        if (second < 10)
                            ":0${second}"
                        else
                            ":${second}"
                    minute_tv.text = "${minute}"
                }
            }
        }
    }

    private fun pause() {
        start_btn.text = "시작"
        start_btn.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()
    }

    private fun refresh() {
        timer?.cancel()

        start_btn.text = "시작"
        start_btn.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        time = 0
        millisecond_tv.text = ".00"
        second_tv.text = ":00"
        minute_tv.text = "00"
    }
}