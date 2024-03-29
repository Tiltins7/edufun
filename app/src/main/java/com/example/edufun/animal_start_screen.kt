@file:Suppress("DEPRECATION")

package com.example.edufun

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Camera
import android.os.*
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_animal_game.*
import android.view.View
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.media.MediaPlayer
import android.widget.Button
import kotlinx.android.synthetic.main.activity_color.*


class animal_start_screen : AppCompatActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    private var myContext: Context? = null
    private var cameraPreview: LinearLayout? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_game)

        changeCharSize()




        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        myContext = this

        btnExit.setOnClickListener{
            val intent = Intent(this, animal_info::class.java)

            startActivity(intent)

        }



        initCamere()

        funcAgain()
        initFunc()
            // TODO need to find the way to call equalString() after QR scann
        //equalString(correctStr, incorrectStr)
   }



    private fun changeCharSize(){
        val randV = intent.getStringExtra("MY_KEY")
        randVal?.text = randV


        when {
            randVal.text == "ELEPHANT" -> randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,45F)
            randVal.text == "MONKEY" -> randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,50F)
            randVal.text == "PENGUIN" -> randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,50F)
        }
    }


            // function to compare the expected and given string values, playing with textView visibility.
            // Need to find a way to call function after scan....
    private fun equalString(view: View, view1: View){

                val dink = MediaPlayer.create(this, R.raw.correct)
        view.visibility = if (randVal.text == et_value.text){
            dink.start()
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

    fun inCorrectVib(){
        val ring = MediaPlayer.create(this, R.raw.beep)

        if (randVal.text != et_value.text)
        {
            ring.start()
            vibration()

        }
    }

    fun vibration(){
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(500) // Vibrate method for below API Level 26
            }
        }
    }


    private fun initCamere() {

        mCamera = Camera.open()
        mCamera!!.setDisplayOrientation(90)
        cameraPreview = findViewById(R.id.dPreview) as LinearLayout
        mPreview = CameraPreview(myContext as animal_start_screen, mCamera)
        cameraPreview!!.addView(mPreview)

        mCamera!!.startPreview()

    }

    private fun initFunc(){
        //button action
        btn_scan_me.setOnClickListener{
            // call scanner function
            initScan()
            btn_reload.visibility = View.VISIBLE

        }
    }

    private fun funcAgain(){
        btn_reload.setOnClickListener {
//
            incorrectStr.visibility = View.INVISIBLE


            val animalList= listOf<String>("CAT","DOG","BIRD","ELEPHANT","MONKEY","PIG","PENGUIN","RABBIT")

            val oneAnimal = animalList.random()


            randVal.text = oneAnimal



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
                    inCorrectVib()


                }
            }
        } else {
            // the camera will be open till result is null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}
