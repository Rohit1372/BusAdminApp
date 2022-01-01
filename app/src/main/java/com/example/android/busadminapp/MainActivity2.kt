package com.example.android.busadminapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {

    private lateinit var floating2 : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        floating2 = findViewById(R.id.floating2)

        floating2.setOnClickListener {
            val intent = Intent(this,Send_Data2::class.java)
            startActivity(intent)
        }

    }
}