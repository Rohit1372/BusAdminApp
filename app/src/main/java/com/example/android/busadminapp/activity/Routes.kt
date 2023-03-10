package com.example.android.busadminapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.adapter.BusAdapter
import com.example.android.busadminapp.adapter.RouteAdapter
import com.example.android.busadminapp.model.Bus
import com.example.android.busadminapp.model.Route
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject

class Routes : AppCompatActivity() {

    private lateinit var fromRoute :TextView
    private lateinit var toRoute :TextView

    private lateinit var addRoute : FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var routeList : ArrayList<Route>
    private lateinit var routeAdapter: RouteAdapter

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routes)

        fromRoute = findViewById(R.id.textFrom)
        toRoute = findViewById(R.id.textTo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "View Routes"

        val id : String = intent.getStringExtra("id").toString()

        val fromR = intent.getStringExtra("from").toString()
        val toR = intent.getStringExtra("to").toString()

        fromRoute.text = fromR
        toRoute.text = toR

        addRoute = findViewById(R.id.addRoute)
        recyclerView = findViewById(R.id.recyclerView)

        routeList = ArrayList()
        routeAdapter = RouteAdapter(this,routeList,id)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = routeAdapter

        val relativeLayout : RelativeLayout = findViewById(R.id.relativeLayout_route)
        progressBar = findViewById(R.id.progressBar_route)

        if(NetworkHelper.isNetworkConnected(this)){
            getData()

        }else{
            progressBar.visibility= View.VISIBLE
            Snackbar.make(relativeLayout,"Sorry! There is no network connection.Please try later.",
                Snackbar.LENGTH_INDEFINITE).show()
        }


        addRoute.setOnClickListener {
            val intent = Intent(this, AddRoute::class.java)
                .putExtra("id",id)
                .putExtra("From",fromR)
                .putExtra("To",toR)
            startActivity(intent)
            //finish()
        }

    }

    fun getData(){
        val db =  Firebase.firestore

        val id : String = intent.getStringExtra("id").toString()

        db.collection("Buses")
            .document(id)
            .get()
            .addOnSuccessListener {
                val result: MutableMap<String, Any>? = it.data
                val routesData = result?.get("Routes")
                if(routesData!=null){
                    val routes = routesData as ArrayList<*>
                    for (values in routes) {
                        var data = values as MutableMap<*, *>
                        routeList.add(
                            Route(
                                data["Stop Number"] as String,
                                data["Stop At"] as String
                            )
                        )
                    }
                }
                progressBar.visibility = View.GONE
                routeAdapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
    }

}