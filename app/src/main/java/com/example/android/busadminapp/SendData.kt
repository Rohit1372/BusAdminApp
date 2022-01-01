package com.example.android.busadminapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.HashMap
import com.google.firebase.firestore.FirebaseFirestore as FirebaseFirestore1

class SendData : AppCompatActivity() {

    private lateinit var from : EditText
    private lateinit var to : EditText
    private lateinit var busService : EditText
    private lateinit var busNo : EditText
    private lateinit var date : EditText
    private lateinit var startingTime :EditText
    private lateinit var arrivalTime : EditText
    private lateinit var price : EditText
    private lateinit var sendBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data)

        from = findViewById(R.id.from)
        to = findViewById(R.id.to)
        busService = findViewById(R.id.busService)
        busNo = findViewById(R.id.busNo)
        date = findViewById(R.id.date)
        startingTime = findViewById(R.id.startingTime)
        arrivalTime = findViewById(R.id.arrivalTime)
        price = findViewById(R.id.price)
        sendBtn = findViewById(R.id.sendData)

        sendBtn.setOnClickListener {
            val from = from.text.toString()
            val to = to.text.toString()
            val busService = busService.text.toString()
            val busNo = busNo.text.toString()
            val date = date.text.toString()
            val startingTime = startingTime.text.toString()
            val arrivalTime = arrivalTime.text.toString()
            val price = price.text.toString()

            if(TextUtils.isEmpty(from) || TextUtils.isEmpty(to) || TextUtils.isEmpty(busService) || TextUtils.isEmpty(busNo) || TextUtils.isEmpty(date) || TextUtils.isEmpty(startingTime) || TextUtils.isEmpty(arrivalTime) || TextUtils.isEmpty(price) ){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            } else{

                saveFireStore(from,to,busService,busNo,date,startingTime,arrivalTime,price)
            }

        }

        //Set Calender dialog
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        date.setOnClickListener {
            DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                date.setText("$dayOfMonth/${month+1}/$year")
            },year,month,day).show()
        }

        //Set Time dialog
        startingTime.setOnClickListener {
            val time = Calendar.getInstance()
            val hour = time.get(Calendar.HOUR_OF_DAY)
            val minute = time.get(Calendar.MINUTE)
            TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{view,hour,minute->
                var AM_PM =""
                var h=0
                if(hour>12){
                    h = hour-12
                    AM_PM = "PM"
                }else{
                    h = hour
                    AM_PM = "AM"
                }
                startingTime.setText("$h:$minute $AM_PM")
            },hour,minute,false).show()
        }

        arrivalTime.setOnClickListener {
            val time = Calendar.getInstance()
            val hour = time.get(Calendar.HOUR_OF_DAY)
            val minute = time.get(Calendar.MINUTE)
            TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{view,hour,minute->
                var AM_PM =""
                var h=0
                if(hour>12){
                    h = hour-12
                    AM_PM = "PM"
                }else{
                    h = hour
                    AM_PM = "AM"
                }
                arrivalTime.setText("$h:$minute $AM_PM")
            },hour,minute,false).show()
        }

    }

    private fun saveFireStore(from : String, to : String, busService:String, busNo:String, date:String, startingTime:String, arrivalTime:String, price:String){
        val db = Firebase.firestore
        val user : MutableMap<String,Any> = HashMap()

        user["From"] = from
        user["To"] = to
        user["Bus Service"] = busService
        user["Bus Number"] = busNo
        user["Date"] = date
        user["Starting Time"] = startingTime
        user["Arrival Time"] = arrivalTime
        user["Price"] = price

        db.collection("Buses")
            .add(user)
            .addOnCompleteListener {

                Toast.makeText(this,"Information added successfully",Toast.LENGTH_SHORT).show()

                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)

            }.addOnFailureListener {
            Toast.makeText(this,"Failed Info. Add",Toast.LENGTH_SHORT).show()
            }
    }

}


