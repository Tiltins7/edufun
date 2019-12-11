package com.example.edufun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_counting_start_screen.*
import android.os.CountDownTimer
import android.view.View
import android.os.Vibrator
import android.os.VibrationEffect
import android.os.Build
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import androidx.core.content.ContextCompat.getSystemService




class counting_game_start: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counting_start_screen)

        object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val string = getString(R.string.countdown, seconds)
                textView15.setText(string)

            }

            override fun onFinish() {
                var iter = 0
                showHide(textView15)
                showHide(textView16)
                val answer = (0 until 11).random()
                while(iter < answer) {
                    vibratePhone()
                    iter = iter + 1
                }

            }
        }.start()

        homebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }

    fun vibratePhone() {
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            //deprecated in API 26
            v.vibrate(1000)
        }
    }
}