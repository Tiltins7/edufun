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
import kotlinx.android.synthetic.main.activity_animal_game.*
import java.util.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_color.*
import kotlin.random.Random



class animalGame : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    private var myContext: Context? = null
    private var cameraPreview: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_game)

       val randV = intent.getStringExtra("MY_KEY")
        randVal?.text = randV


        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myContext = this

        btnExit.setOnClickListener{
            val intent = Intent(this, Animal::class.java)

            startActivity(intent)
        }

        initCamere()

        initFunc()
            // TODO need to find the way to call equalString() after QR scann
        //equalString(correctStr, incorrectStr)

    }


            // function to compare the expected and given string values, playing with textView visibility.
            // Need to find a way to call function after scan....
    private fun equalString(view: View, view1: View){

        view.visibility = if (randVal.text == et_value.text){
            View.VISIBLE
        } else{
            View.INVISIBLE

        }
        view1.visibility = if (randVal.text == et_value.text){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }

    }


    private fun initCamere() {

        mCamera = Camera.open()
        mCamera!!.setDisplayOrientation(90)
        cameraPreview = findViewById(R.id.dPreview) as LinearLayout
        mPreview = CameraPreview(myContext as animalGame, mCamera)
        cameraPreview!!.addView(mPreview)

        mCamera!!.startPreview()

    }

    private fun initFunc(){
        //button action
        btn_scan_me.setOnClickListener{
            // call scanner function
            initScan()


        }
    }

    private fun initScan(){
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        //check if result scan any QR code
        if(result != null){
            if ((result.contents == null))
            { //the result data is empty
                Toast.makeText(this, "The data is empty.", Toast.LENGTH_LONG).show()
            } else {
                et_value.setText(result.contents.toString())
                if (result.contents != null)
                {
                    equalString(correctStr, incorrectStr)
                }
            }
        } else {
            // the camera will be open till result is null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
