package com.example.android.busadminapp.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.busadminapp.R
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
    private lateinit var stopTime : EditText
    private lateinit var addRoute : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        //Remove Action Bar
        supportActionBar?.hide()

        from = findViewById(R.id.from_add_routes)
        to = findViewById(R.id.to_add_routes)

        val id :String = intent.getStringExtra("id").toString()

        val routeFrom = intent.getStringExtra("From").toString()
        val routeTo = intent.getStringExtra("To").toString()

        from.text = routeFrom
        to.text = routeTo

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


        stopTime.setOnClickListener {
            val time = Calendar.getInstance()
            val hour = time.get(Calendar.HOUR_OF_DAY)
            val minute = time.get(Calendar.MINUTE)
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{ view, hour, minute->
                var AM_PM =""
                var h=0
                if(hour>12){
                    h = hour-12
                    AM_PM = "PM"
                }else{
                    h = hour
                    AM_PM = "AM"
                }

                var m=""
                if(minute>=0 && minute<10){
                    m = "0$minute"
                }
                else{
                    m = "$minute"
                }
                stopTime.setText("$h:$m $AM_PM")
            },hour,minute,false).show()
        }


    }

    private fun saveFireStore(stopNo : String, stopAt : String, stopTime:String, id:String) {
        val db = Firebase.firestore
        val route: MutableMap<String, Any> = HashMap()
        route["Stop Number"] = stopNo
        route["Stop At"] = stopAt
        route["Stop Time"] = stopTime

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