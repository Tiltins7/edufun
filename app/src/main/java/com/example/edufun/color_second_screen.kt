@file:Suppress("DEPRECATION")
package com.example.edufun

import android.R.attr.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_color_start.*
import android.content.ContentValues
import android.provider.MediaStore
import android.net.Uri
import android.app.Activity
import android.content.Context
import android.widget.ImageView
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import android.media.MediaPlayer
import android.os.Vibrator

import kotlinx.android.synthetic.main.activity_counting_start_screen.*



class color_second_screen: AppCompatActivity (){
    var image_uri: Uri? = null
    private val IMAGE_CAPTURE_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_start)

        randomColorInit()

        btnExitcolor.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

        }

        btn_take_color.setOnClickListener {

            openCamera()
        }

        btn_color_playagain.setOnClickListener{
            val colorList= listOf<String>("RED", "BLUE", "GREEN","BLACK")
            val onecolor = colorList.random()
            val intent = Intent(this, color_second_screen::class.java)
            intent.putExtra("random_color", onecolor)
            startActivity(intent)
        }

        btn_image_ok.setOnClickListener{
            val x =imageView2.getX() + imageView2.getWidth()  / 2
            val y =imageView2.getY() + imageView2.getHeight() / 2
            val bitmap = (imageView2.getDrawable() as BitmapDrawable).bitmap
            val pixel = bitmap.getPixel(x.toInt(), y.toInt())
            val redValue = Color.red(pixel)
            val blueValue = Color.blue(pixel)
            val greenValue = Color.green(pixel)
            val ring = MediaPlayer.create(this, R.raw.beep)
            val dink = MediaPlayer.create(this, R.raw.correct)
            val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            var choice = ""

            if(maxOf(redValue,blueValue,greenValue) == greenValue){
                choice = "GREEN"
            }
            if(maxOf(redValue,blueValue,greenValue) == redValue){
                choice = "RED"
            }
            if(maxOf(redValue,blueValue,greenValue) == blueValue){
                choice = "BLUE"
            }
            if(maxOf(redValue,blueValue,greenValue) < 100){
                choice = "BLACK"
            }


            val answer = intent.getStringExtra("random_color")

            if(choice == answer){
                answer_color.setText("$choice")
                dink.start()
                frameLayout_ask_color.visibility = View.VISIBLE
                framelayout_youranswer.visibility = View.VISIBLE
                image_color_answ_correct.visibility = View.VISIBLE
                btn_color_playagain.visibility = View.VISIBLE
                framelayout_image.visibility = View.INVISIBLE
            }else{
                answer_color.setText("$choice")
                ring.start()
                v.vibrate(600)
                frameLayout_ask_color.visibility = View.VISIBLE
                framelayout_youranswer.visibility = View.VISIBLE
                image_color_answ_incorrect.visibility = View.VISIBLE
                btn_color_playagain.visibility = View.VISIBLE
                framelayout_image.visibility = View.INVISIBLE
            }


        }


    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 7 && resultCode == RESULT_OK) {
            val intent = Intent(this, color_second_screen::class.java)

            startActivity(intent)
            framelayout_image.visibility = View.VISIBLE
            var taken_image : ImageView
            val bitmap = data!!.extras!!.get("data") as Bitmap?
            taken_image = findViewById(R.id.framelayout_image)
            taken_image.setImageBitmap(bitmap)
        }
    }*/

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //called when image was captured from camera intent

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            btn_image_ok.visibility = View.VISIBLE
            framelayout_image.visibility = View.VISIBLE
            frameLayout_ask_color.visibility = View.INVISIBLE
            imageView2.setImageURI(image_uri)
        }
    }

     private fun randomColorInit() {
        val randColor = intent.getStringExtra("random_color")
        randomColorTW?.text = randColor

    }

    }