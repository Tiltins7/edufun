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

class animalGame : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    private var myContext: Context? = null
    private var cameraPreview: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_game)


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
            }
        } else {
            // the camera will be open till result is null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
