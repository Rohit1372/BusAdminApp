package com.example.android.busadminapp.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.example.android.busadminapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    lateinit var busList : CardView
    lateinit var logOut : CardView
    private lateinit var home_back_arrow : ImageView
    private lateinit var profile : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        busList = findViewById(R.id.busList)
        logOut = findViewById(R.id.log_out)
        home_back_arrow = findViewById(R.id.home_back_arrow)
        profile = findViewById(R.id.profile)

        home_back_arrow.setOnClickListener {
            onBackPressed()
        }

        profile.setOnClickListener {
            val intent = Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        busList.setOnClickListener {
            val intent = Intent(this,Buses::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        logOut.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure!")
            builder.setMessage("Do you want LogOut?")
            builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
                auth.signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            })
            builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
            })
            builder.create()
            builder.show()
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close this app?")
        builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
        })
        builder.create()
        builder.show()
    }

}
