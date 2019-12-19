@file:Suppress("DEPRECATION")

package com.example.edufun


import android.content.Context
import android.content.Intent
import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.speech.tts.TextToSpeech
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_color.*
import kotlinx.android.synthetic.main.activity_info.*
import java.util.*

class color : AppCompatActivity() {

            private var mCamera: Camera? = null
            private var mPreview: CameraPreview? = null
            private var myContext: Context? = null
            private var cameraPreview: LinearLayout? = null

            lateinit var mTTS: TextToSpeech


            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_color)


                mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
                    if (status != TextToSpeech.ERROR){
                        //if there is no error then set language
                        mTTS.language = Locale.UK
                    }
                })

                btnAudioInfo.setOnClickListener {
                    //get text from edit text
                    val toSpeak = textView.text.toString()
                    val toSpeak1 = textView4.text.toString()
                    val toSpeak2 = textView5.text.toString()
                    val toSpeak3 = textView6.text.toString()
                    val toSpeak4 = textView7.text.toString()

                    mTTS.speak(toSpeak, TextToSpeech.QUEUE_ADD, null)


                    mTTS.speak(toSpeak1, TextToSpeech.QUEUE_ADD, null)


                    mTTS.speak(toSpeak2, TextToSpeech.QUEUE_ADD, null)

                    mTTS.speak(toSpeak3, TextToSpeech.QUEUE_ADD, null)

                    mTTS.speak(toSpeak4, TextToSpeech.QUEUE_ADD, null)
                }



                homebtn.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                }

                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())

                window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                myContext = this

                initCamere()


            }

            private fun initCamere() {

                mCamera = Camera.open()
                mCamera!!.setDisplayOrientation(90)
                cameraPreview = findViewById(R.id.cPreview) as LinearLayout
                mPreview = CameraPreview(myContext as color, mCamera)
                cameraPreview!!.addView(mPreview)
                
                mCamera!!.startPreview()

           }


        }

