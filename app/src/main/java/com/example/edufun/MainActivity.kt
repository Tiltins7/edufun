@file:Suppress("DEPRECATION")

package com.example.edufun


import java.util.*
import android.content.Context
import android.content.res.Configuration
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button4.setOnClickListener {
            val intent = Intent(this, counting_info::class.java)
            startActivity(intent)

        }

        btnAnimal.setOnClickListener {
            val intent = Intent(this, animal_info::class.java)
            startActivity(intent)
        }

        btn_info.setOnClickListener{
            val intent = Intent(this, info::class.java)
            startActivity(intent)
        }

        btnColor.setOnClickListener{
            val intent = Intent(this, color::class.java)
            startActivity(intent)
        }

        langbutton.setOnClickListener{
            showChangeLang()
        }

        btnColChange.setOnClickListener{
            changeAppColor()
        }

    }

    private fun changeAppColor() {
        val listItems = arrayOf("Light Blue/Dark Blue", "Original")
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Color")
        mBuilder.setSingleChoiceItems(listItems,-1){dialog, number ->
            if (number == 0){
                mainScreen.setBackgroundColor(Color.parseColor("#B3D1EF"))
                btnColor.setBackgroundColor(Color.parseColor("#2B3196"))
                btnAnimal.setBackgroundColor(Color.parseColor("#2B3196"))
                button4.setBackgroundColor(Color.parseColor("#2B3196"))
                button2.setBackgroundColor(Color.parseColor("#2B3196"))
                langbutton.setBackgroundColor(Color.parseColor("#2B3196"))
                btnColChange.setBackgroundColor(Color.parseColor("#2B3196"))
                btn_info.setBackgroundColor(Color.parseColor("#2B3196"))
            }
            if (number == 1){
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }


    private fun showChangeLang() {
        val listItems = arrayOf("english","español")
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItems,-1){dialog, number ->
            if (number == 0){
                setLocate("en")
                recreate()
            }
            if (number == 1){
                setLocate("es")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocate(Lang: String){
        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }
    }


