@file:Suppress("DEPRECATION")

package com.example.edufun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_counting_info.*
import kotlinx.android.synthetic.main.activity_counting_info.homebtn
import java.util.*

class counting_info : AppCompatActivity() {

    lateinit var mTTS: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counting_info)

        homebtn.setOnClickListener {

            if (mTTS.isSpeaking){
                mTTS.stop()
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.UK
            }
        })

        btnAudioInfo3.setOnClickListener{
            val toSpeak = textView14.text.toString()
            val toSpeak1 = textView16.text.toString()
            val toSpeak2 = textView17.text.toString()
            val toSpeak3 = textView19.text.toString()



            mTTS.speak(toSpeak, TextToSpeech.QUEUE_ADD, null)
            mTTS.speak(toSpeak1, TextToSpeech.QUEUE_ADD, null)
            mTTS.speak(toSpeak2, TextToSpeech.QUEUE_ADD, null)
            mTTS.speak(toSpeak3, TextToSpeech.QUEUE_ADD, null)

        }

        countingStart.setOnClickListener{
            if (mTTS.isSpeaking){
                mTTS.stop()
            }

            val intent = Intent(this, counting_game_start::class.java)
            startActivity(intent)
        }

    }
}
