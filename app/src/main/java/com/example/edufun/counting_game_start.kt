package com.example.edufun



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_counting_start_screen.*
import android.os.CountDownTimer
import android.view.View
import android.os.Vibrator
import android.os.VibrationEffect
import android.os.Build
import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.text.Layout
import androidx.core.content.ContextCompat.getSystemService
import java.nio.file.Files.size
import java.util.Arrays
import java.util.ArrayList
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer





class counting_game_start: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counting_start_screen)

        object : CountDownTimer(5000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val string = getString(R.string.countdown, seconds)
                textView15.setText(string)

            }

            override fun onFinish() {
                textView15.visibility = View.INVISIBLE
                textView16.visibility = View.VISIBLE


                vibratePhone()
                Thread.sleep(7000)
                setAnswers()

            }
        }.start()

        homebtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

    fun showHide(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE){
            View.INVISIBLE
        } else{
            View.VISIBLE
        }
    }

    fun vibratePhone() {

        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val mVibratePattern = longArrayOf(700, 1000,700, 1000,700, 1000,700, 1000)
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createWaveform(mVibratePattern, -1))
        } else {
            //deprecated in API 26
            v.vibrate(mVibratePattern, -1)
        }
    }

    fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setMessage("Are you sure about your answer?")
        builder.setPositiveButton("Yes") { dialog, id ->
            dialog.dismiss()

        }
        builder.setNegativeButton(
            "No"
        ) { dialog, id -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    fun setAnswers(){
        val answer = 4
        var option1 = (1..6).random()
        var option2 = (1..6).random()
        if(option1 == answer){
            option1 = option1 + 1
        }
        if(option2 == answer){
            option2 = option2 + 1
        }

        val choice = (1..3).random()
        val btn1 = findViewById(R.id.btn_answ1) as Button
        val btn2 = findViewById(R.id.btn_answ2) as Button
        val btn3 = findViewById(R.id.btn3) as Button
        if (choice == 1){
            btn1.setText(option1.toString())
            btn2.setText(option2.toString())
            btn3.setText(answer.toString())

        } else if(choice == 2){
            btn3.setText(option1.toString())
            btn1.setText(option2.toString())
            btn2.setText(answer.toString())
        }else{
            btn3.setText(option2.toString())
            btn1.setText(answer.toString())
            btn2.setText(option1.toString())
        }

        val start_view = findViewById(R.id.frameLayout3) as View
        val answer_view = findViewById(R.id.frameLayout4) as View
        showHide(start_view)
        showHide(answer_view)
        btn1.setVisibility(View.VISIBLE)
        btn2.setVisibility(View.VISIBLE)
        btn3.setVisibility(View.VISIBLE)


        val correct = "CORRECT!"
        val incorrect = "INCORRECT!"
        val cor_string = getString(R.string.counting_finish_correct, correct)
        val incor_string = getString(R.string.counting_finish_incorrect,incorrect)
        val ring = MediaPlayer.create(this, R.raw.beep)
        val dink = MediaPlayer.create(this, R.raw.correct)
        val v = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        btn_answ1.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage("You chose "+ btn_answ1.text + "\n" + "Are you sure about your answer?")
            builder.setPositiveButton("Yes") { dialog, id ->
                dialog.dismiss()
                if(btn_answ1.text == answer.toString()){
                    homebtn.visibility = View.INVISIBLE
                    dink.start()
                    textView_correct.setText(cor_string)
                    imageViewAnswer.setImageResource(R.drawable.checkmark)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    homebtn.visibility = View.INVISIBLE
                    ring.start()
                    v.vibrate(600)
                    textView_correct.setText(incor_string)
                    imageViewAnswer.setImageResource(R.drawable.red_cross)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            builder.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()


        }


        btn_answ2.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage("You chose "+ btn_answ2.text + "\n" + "Are you sure about your answer?")
            builder.setPositiveButton("Yes") { dialog, id ->
                dialog.dismiss()
                if(btn_answ2.text == answer.toString()){
                    homebtn.visibility = View.INVISIBLE
                    dink.start()
                    textView_correct.setText(cor_string)
                    imageViewAnswer.setImageResource(R.drawable.checkmark)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    homebtn.visibility = View.INVISIBLE
                    ring.start()
                    v.vibrate(600)
                    textView_correct.setText(incor_string)
                    imageViewAnswer.setImageResource(R.drawable.red_cross)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            builder.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()

        }
        btn3.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage("You chose "+ btn3.text + "\n" + "Are you sure about your answer?")
            builder.setPositiveButton("Yes") { dialog, id ->
                dialog.dismiss()
                if(btn3.text == answer.toString()){
                    homebtn.visibility = View.INVISIBLE
                    dink.start()
                    textView_correct.setText(cor_string)
                    imageViewAnswer.setImageResource(R.drawable.checkmark)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    homebtn.visibility = View.INVISIBLE
                    ring.start()
                    v.vibrate(600)
                    textView_correct.setText(incor_string)
                    imageViewAnswer.setImageResource(R.drawable.red_cross)
                    showHide(answer_view)
                    btn1.setVisibility(View.INVISIBLE)
                    btn2.setVisibility(View.INVISIBLE)
                    btn3.setVisibility(View.INVISIBLE)
                    button_play_again.setVisibility(View.VISIBLE)
                    button_play_exit.setVisibility(View.VISIBLE)
                    val finish_view = findViewById(R.id.frameLayout_finish) as View
                    showHide(finish_view)
                    button_play_again.setOnClickListener{
                        val intent = Intent(this, counting_game_start::class.java)
                        startActivity(intent)
                    }
                    button_play_exit.setOnClickListener{
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            builder.setNegativeButton(
                "No"
            ) { dialog, id -> dialog.dismiss() }
            val alert = builder.create()
            alert.show()
        }

    }

}