package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.busadminapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Routes : AppCompatActivity() {

    private lateinit var floating2 : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        floating2 = findViewById(R.id.floating2)

        floating2.setOnClickListener {
            val intent = Intent(this, AddRoute::class.java)
            startActivity(intent)
        }

    }
}