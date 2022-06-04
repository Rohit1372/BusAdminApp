package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.android.busadminapp.R

class SplashScreenActivity : AppCompatActivity() {
    lateinit var splashImage : ImageView
    lateinit var splashText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        splashImage = findViewById(R.id.splashimage1)
        splashText = findViewById(R.id.splashText)

        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        splashImage.startAnimation(topAnim)
        splashText.startAnimation(bottomAnim)

        val splashScreenTimeOut = 3000
        val intent = Intent(this,LoginActivity::class.java)
        Handler().postDelayed({
            startActivity(intent)
            finish()
        },splashScreenTimeOut.toLong())

    }
}