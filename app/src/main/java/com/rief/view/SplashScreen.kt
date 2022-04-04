package com.rief.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rief.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper())

        val delayTime = 2000
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, delayTime.toLong())

    }
}