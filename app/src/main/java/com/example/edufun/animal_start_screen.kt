@file:Suppress("DEPRECATION")

package com.example.edufun

import android.content.Context
import android.content.Intent
import android.hardware.Camera
import android.opengl.Visibility
import android.os.*
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_animal_game.*
import android.view.View





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

        initFunc()
            // TODO need to find the way to call equalString() after QR scann
        //equalString(correctStr, incorrectStr)

    }


    private fun changeCharSize(){



        val randV = intent.getStringExtra("MY_KEY")
        randVal?.text = randV

        if (randVal.text == "ELEPHANT" )
        {
            randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,45F)
        } else if (randVal.text == "MONKEY")
        {
            randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,50F)
        }
        else if (randVal.text == "PENGUIN")
        {
            randVal.setTextSize(TypedValue.COMPLEX_UNIT_SP,50F)
        }


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

    fun inCorrectVib(){
        if (randVal.text != et_value.text)
        {
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
