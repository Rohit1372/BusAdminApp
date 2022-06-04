package com.example.android.busadminapp.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.adapter.BusAdapter
import com.example.android.busadminapp.model.Bus
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

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
            //finish()
        }

        getData()

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close this app?")
        builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface , i:Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface : DialogInterface , i:Int ->
        })
        builder.create()
        builder.show()
    }

    fun getData(){
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

                    busList.add(
                        Bus(id,from,to,busService,busNo,date,startingTime,arrivalTime,price)
                    )
                }

                busAdapter.notifyDataSetChanged()

            }.addOnFailureListener {
                Toast.makeText(this,"$it",Toast.LENGTH_SHORT).show()
            }
    }

    //Searching

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_buses,menu)
        var searchItem=menu?.findItem(R.id.searchBtn)
        var searchView=searchItem?.actionView as SearchView;
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                busAdapter.getFilter().filter(newText)
                return false
            }
        })
        return true
    }

}