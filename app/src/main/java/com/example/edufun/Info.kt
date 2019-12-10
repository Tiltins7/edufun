@file:Suppress("DEPRECATION")
package com.example.edufun

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_info.*

import java.util.*

class Info : AppCompatActivity() {

    //Text To Speech
    lateinit var mTTS:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        homeBtnInf.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.UK
            }
        })

        btnAudio.setOnClickListener {
            //get text from edit text
            val toSpeak = textView2.text.toString()
            if (toSpeak == ""){
                //if there is no text in edit text
                Toast.makeText(this, "Enter text", Toast.LENGTH_SHORT).show()
            }
            else{
                //if there is text in edit text
                Toast.makeText(this, toSpeak, Toast.LENGTH_SHORT).show()
                mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null)
            }
        }

    }
}