package com.example.android.busadminapp.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import com.example.android.busadminapp.R
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap

class AddRoute : AppCompatActivity() {

    private lateinit var from : TextView
    private lateinit var to : TextView

    private lateinit var stopNo : EditText
    private lateinit var stopAt : EditText
    //private lateinit var stopTime : EditText
    private lateinit var addRoute : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add Bus Routes"

        val id :String = intent.getStringExtra("id").toString()

        stopNo = findViewById(R.id.stopNo)
        stopAt = findViewById(R.id.stopAt)

        addRoute = findViewById(R.id.add_route)

        val layout : ScrollView = findViewById(R.id.addRouteLayout)

        addRoute.setOnClickListener {

            if(NetworkHelper.isNetworkConnected(this)){
                val stopNo = stopNo.text.toString()
                val stopAt = stopAt.text.toString()

                if(TextUtils.isEmpty(stopNo) || TextUtils.isEmpty(stopAt) ){
                    Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
                } else{

                    saveFireStore(stopNo,stopAt,id)

                }

            }else{
                Snackbar.make(layout,"Sorry! There is no network connection.Please try later",
                    Snackbar.LENGTH_SHORT).show()
            }

        }

    }

    private fun saveFireStore(stopNo : String, stopAt : String, id:String) {
        val db = Firebase.firestore
        val route: MutableMap<String, Any> = HashMap()
        route["Stop Number"] = stopNo
        route["Stop At"] = stopAt

        db.collection("Buses").document(id)
            .update("Routes", FieldValue.arrayUnion(route))
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