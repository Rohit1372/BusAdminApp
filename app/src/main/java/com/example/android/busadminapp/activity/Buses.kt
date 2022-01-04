package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.adapter.BusAdapter
import com.example.android.busadminapp.model.Bus
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Buses : AppCompatActivity() {

    private lateinit var addBus : FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var busList : ArrayList<Bus>
    private lateinit var busAdapter: BusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses)

        busList = ArrayList()

        addBus = findViewById(R.id.addBus)
        recyclerView = findViewById(R.id.recyclerView)

        busAdapter = BusAdapter(this,busList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = busAdapter

        addBus.setOnClickListener {
            val intent = Intent(this, AddBus::class.java)
            startActivity(intent)
            finish()
        }

        val db =  Firebase.firestore

        db.collection("Buses").orderBy("From", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val result = it.documents

                for ( values in result ){
                    val id = values.id
                    val from = values["From"].toString()
                    val to = values["To"].toString()
                    val busService = values["Bus Service"].toString()
                    val busNo = values["Bus Number"].toString()
                    val date = values["Date"].toString()
                    val startingTime = values["Starting Time"].toString()
                    val arrivalTime = values["Arrival Time"].toString()
                    val price = values["Price"].toString()

                    busList.add(Bus(id,from,to,busService,busNo,date,startingTime,arrivalTime,price))
                }

                busAdapter.notifyDataSetChanged()

            }.addOnFailureListener {
                Toast.makeText(this,"$it",Toast.LENGTH_SHORT).show()
            }

    }

}

/*private fun addInfo(){
        val inflater = LayoutInflater.from(this)
        val v = inflater.inflate(R.layout.add_item,null)

        val dialogDesti = v.findViewById<EditText>(R.id.dialogDesti)
        val dialogServise = v.findViewById<EditText>(R.id.dialogService)
        val dialogTime = v.findViewById<EditText>(R.id.dialogTime)
        val dialogRate = v.findViewById<EditText>(R.id.dialogRate)
        val dialogDate = v.findViewById<EditText>(R.id.dialogDate)

        val addDialog = AlertDialog.Builder(this)
        addDialog.setView(v)
        addDialog.setPositiveButton("Ok"){
            dialog,_->
            val dDesti = dialogDesti.text.toString()
            val dService = dialogServise.text.toString()
            val dTime = dialogTime.text.toString()
            val dRate =  dialogRate.text.toString()
            val dDate = dialogDate.text.toString()
            if(TextUtils.isEmpty(dDesti) || TextUtils.isEmpty(dService) || TextUtils.isEmpty(dTime) || TextUtils.isEmpty(dRate) || TextUtils.isEmpty(dDate)){
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()
            } else {
                userList.add(UserData("$dDesti","$dService","$dTime","Rs. $dRate","$dDate"))
                userAdapter.notifyDataSetChanged()
                Toast.makeText(this,"Added Information Success",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        addDialog.setNegativeButton("Cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }
        addDialog.create()
        addDialog.show()
    }*/