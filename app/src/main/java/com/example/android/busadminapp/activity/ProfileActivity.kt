package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.android.busadminapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {

    lateinit var profile_name : TextView
    lateinit var profile_phone : TextView
    lateinit var profile_email : TextView

    var databaseReference: DatabaseReference?=null
    var database : FirebaseDatabase?=null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profile_name = findViewById(R.id.profile_name)
        profile_phone = findViewById(R.id.profile_phone)
        profile_email = findViewById(R.id.profile_email)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()

    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)
        profile_email.text = user?.email

        userReference?.addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    profile_name.text = snapshot.child("name").value.toString()
                    profile_phone.text = snapshot.child("phoneNo").value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )

    }
}