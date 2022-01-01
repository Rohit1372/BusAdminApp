package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.android.busadminapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddRoute : AppCompatActivity() {

    private lateinit var stopNo : EditText
    private lateinit var stopAt : EditText
    private lateinit var stopTime : EditText
    private lateinit var sendBtn2 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        stopNo = findViewById(R.id.stopNo)
        stopAt = findViewById(R.id.stopAt)
        stopTime = findViewById(R.id.stopTime)

        sendBtn2 = findViewById(R.id.sendData2)

        sendBtn2.setOnClickListener {
            val stopNo = stopNo.text.toString()
            val stopAt = stopAt.text.toString()
            val stoptime = stopTime.text.toString()

            if(TextUtils.isEmpty(stopNo) || TextUtils.isEmpty(stopAt) || TextUtils.isEmpty(stoptime) ){
                Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else{

                saveFireStore(stopNo,stopAt,stoptime)
            }
        }

    }

    fun saveFireStore(stopNo : String, stopAt : String, stoptime:String){
        val db = Firebase.firestore
        val user : MutableMap<String,Any> = HashMap()
        user["Stop Number "] = stopNo
        user["Stop At"] = stopAt
        user["Stop Time "] = stoptime

        db.collection("Buses").document()
    }
}