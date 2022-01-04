package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.busadminapp.R
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddRoute : AppCompatActivity() {

    private lateinit var stopNo : EditText
    private lateinit var stopAt : EditText
    private lateinit var stopTime : EditText
    private lateinit var addRoute : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        val id :String = intent.getStringExtra("id").toString()

        stopNo = findViewById(R.id.stopNo)
        stopAt = findViewById(R.id.stopAt)
        stopTime = findViewById(R.id.stopTime)

        addRoute = findViewById(R.id.add_route)

        addRoute.setOnClickListener {
            val stopNo = stopNo.text.toString()
            val stopAt = stopAt.text.toString()
            val stopTime = stopTime.text.toString()

            if(TextUtils.isEmpty(stopNo) || TextUtils.isEmpty(stopAt) || TextUtils.isEmpty(stopTime) ){
                Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else{

                saveFireStore(stopNo,stopAt,stopTime,id)
            }
        }

    }

    private fun saveFireStore(stopNo : String, stopAt : String, stopTime:String, id:String) {
        val db = Firebase.firestore
        val route: MutableMap<String, Any> = HashMap()
        route["Stop Number"] = stopNo
        route["Stop At"] = stopAt
        route["Stop Time"] = stopTime

        db.collection("Buses").document(id).update("Routes", FieldValue.arrayUnion(route))
            .addOnCompleteListener {

                Toast.makeText(this, "Route added successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, Buses::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Failed To Add Route", Toast.LENGTH_SHORT).show()
            }
    }
}