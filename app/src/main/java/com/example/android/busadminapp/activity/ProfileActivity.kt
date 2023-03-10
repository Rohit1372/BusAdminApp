package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.android.busadminapp.R
import com.example.android.busadminapp.model.Bus
import com.example.android.busadminapp.model.Route
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

        getData()

    }

    fun getData() {
        val db = Firebase.firestore
        val user = auth.currentUser!!.uid
        db.collection("AdminProfile").document(user)
            .get()
            .addOnSuccessListener {
                val admin: MutableMap<String, Any> = it.data!!
                profile_name.text = admin["name"].toString()
                profile_phone.text = admin["phoneNo"].toString()
                profile_email.text = admin["email"].toString()

            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
    }
}