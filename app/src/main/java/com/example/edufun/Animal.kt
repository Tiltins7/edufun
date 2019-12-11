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
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_animal.*
import kotlinx.android.synthetic.main.activity_animal.btnAudioAnimal
import kotlinx.android.synthetic.main.activity_animal.homebtn
import kotlinx.android.synthetic.main.activity_animal_game.*
import kotlinx.android.synthetic.main.activity_color.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class Animal : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    private var myContext: Context? = null
    private var cameraPreview: LinearLayout? = null

    lateinit var mTTS: TextToSpeech



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal)


        mTTS = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR){
                //if there is no error then set language
                mTTS.language = Locale.UK
            }
        })

        btnAudioAnimal.setOnClickListener {
            //get text from view text
            val toSpeak = textView8.text.toString()
            val toSpeak1 = textView9.text.toString()
            val toSpeak2 = textView10.text.toString()
            val toSpeak3 = textView11.text.toString()
            val toSpeak4 = textView12.text.toString()


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

        animalStart.setOnClickListener {


            // Creating list here,
            val animalList= listOf<String>("CAT","Dog","Elephant")

            // then randomly take one element from the list
            val oneAnimal = animalList.random()



            val intent = Intent(this, animalGame::class.java)
            //Passing string value from this activity to animalGame, put string element from list and add to intent - name "MY_KEY",
            intent.putExtra("MY_KEY", oneAnimal)
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
        cameraPreview = findViewById(R.id.aPreview) as LinearLayout
        mPreview = CameraPreview(myContext as Animal, mCamera)
        cameraPreview!!.addView(mPreview)

        mCamera!!.startPreview()

    }


}
