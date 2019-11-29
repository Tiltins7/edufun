@file:Suppress("DEPRECATION")

package com.example.edufun
import java.util.*
import android.content.Context
import android.content.res.Configuration
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
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

        langbutton.setOnClickListener{

            showChangeLang()

        }


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

