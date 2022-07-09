package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.material.snackbar.Snackbar

class SplashScreenActivity : AppCompatActivity() {
    lateinit var splashImage : ImageView
    lateinit var splashText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        val mainLayout = findViewById<RelativeLayout>(R.id.mainLayout)

        splashImage = findViewById(R.id.splashimage1)
        splashText = findViewById(R.id.splashText)

        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        splashImage.startAnimation(topAnim)
        splashText.startAnimation(bottomAnim)

        val splashScreenTimeOut = 3000

        if(NetworkHelper.isNetworkConnected(this)){
            val intent = Intent(this,LoginActivity::class.java)
            Handler().postDelayed({
                startActivity(intent)
                finish()
            },splashScreenTimeOut.toLong())
        }else{
            Snackbar.make(mainLayout,"Sorry! There is no network connection.Please try later",Snackbar.LENGTH_INDEFINITE).show()
        }

    }
}