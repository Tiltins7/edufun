package com.example.edufun

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {

            //var camera = Intent(this, color::class.java "android.media.action.IMAGE_CAPTURE")
           //startActivity(camera)

        }
        button4.setOnClickListener {
            val intent = Intent(this, NewActivity::class.java)
            startActivity(intent)

        }
    }
}

