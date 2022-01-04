package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.adapter.BusAdapter
import com.example.android.busadminapp.adapter.RouteAdapter
import com.example.android.busadminapp.model.Bus
import com.example.android.busadminapp.model.Route
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject

class Routes : AppCompatActivity() {

    private lateinit var fromRoute : TextView
    private lateinit var toRoute : TextView
    private lateinit var busServiceRoute : TextView
    private lateinit var busNoRoute : TextView
    private lateinit var dateRoute : TextView
    private lateinit var startTimeRoute : TextView
    private lateinit var arrivalTimeRoute : TextView

    private lateinit var addRoute : FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var routeList : ArrayList<Route>
    private lateinit var routeAdapter: RouteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        fromRoute = findViewById(R.id.from_routes)
        toRoute = findViewById(R.id.to_routes)
        busServiceRoute = findViewById(R.id.busService_routes)
        busNoRoute = findViewById(R.id.busNo_routes)
        dateRoute = findViewById(R.id.date_routes)
        startTimeRoute = findViewById(R.id.startTime_routes)
        arrivalTimeRoute = findViewById(R.id.arrivalTime_routes)

        val id : String = intent.getStringExtra("id").toString()

        val fromR = intent.getStringExtra("from").toString()
        val toR = intent.getStringExtra("to").toString()
        val busServiceR = intent.getStringExtra("bus service").toString()
        val busNoR = intent.getStringExtra("bus no.").toString()
        val dateR = intent.getStringExtra("date").toString()
        val startTimeR = intent.getStringExtra("start time").toString()
        val arrivalTimeR = intent.getStringExtra("arrival time").toString()

        fromRoute.text = fromR
        toRoute.text = toR
        busServiceRoute.text = busServiceR
        busNoRoute.text = busNoR
        dateRoute.text = dateR
        startTimeRoute.text = startTimeR
        arrivalTimeRoute.text = arrivalTimeR


        addRoute = findViewById(R.id.addRoute)
        recyclerView = findViewById(R.id.recyclerView)

        routeList = ArrayList()
        routeAdapter = RouteAdapter(this,routeList,id)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = routeAdapter

        val db =  Firebase.firestore

        db.collection("Buses").document(id).get().addOnSuccessListener {
            val result: MutableMap<String, Any>? = it.data
                val routesData = result?.get("Routes")
if(routesData!=null){
                val routes = routesData as ArrayList<*>
                for (values in routes) {
                    var data = values as MutableMap<*, *>
                    routeList.add(
                        Route(
                            data["Stop Number"] as String,
                            data["Stop At"] as String,
                            data["Stop Time"] as String
                        )
                    )
                }
                routeAdapter.notifyDataSetChanged()
            }
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }

        addRoute.setOnClickListener {
            val intent = Intent(this, AddRoute::class.java).putExtra("id",id)
            startActivity(intent)
            finish()
        }

    }
}