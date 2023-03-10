package com.example.android.busadminapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.R.id.relativeLayout_bookedseat
import com.example.android.busadminapp.adapter.BookedSeatAdapter
import com.example.android.busadminapp.model.BookedSeat
import com.example.android.busadminapp.utils.NetworkHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookedSeatsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var bookedSeatlist : ArrayList<BookedSeat>
    private lateinit var bookedSeatAdapter: BookedSeatAdapter

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_seats)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Booked seats details"

        recyclerView = findViewById(R.id.recyclerView_bookedseat)

        val id : String = intent.getStringExtra("id").toString()

        bookedSeatlist = ArrayList()
        bookedSeatAdapter = BookedSeatAdapter(this,bookedSeatlist,id)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bookedSeatAdapter

        val relativeLayout : RelativeLayout = findViewById(relativeLayout_bookedseat)
        progressBar = findViewById(R.id.progressBar_bookedSeat)

        //progressBar.visibility=View.VISIBLE

        if(NetworkHelper.isNetworkConnected(this)){
            getData()

        }else{
            progressBar.visibility= View.VISIBLE
            Snackbar.make(relativeLayout,"Sorry! There is no network connection.Please try later",
                Snackbar.LENGTH_INDEFINITE).show()
        }

        // Swipe to delete
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                bookedSeatAdapter.deleteItem(viewHolder.adapterPosition)
            }

        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    fun getData(){
        val db =  Firebase.firestore

        val id : String = intent.getStringExtra("id").toString()

        db.collection("Buses")
            .document(id)
            .get()
            .addOnSuccessListener {
                val result: MutableMap<String, Any>? = it.data
                val bookedData = result?.get("BookedSeats")
                if(bookedData!=null){
                    val booked = bookedData as ArrayList<*>
                    for (values in booked) {
                        var seat = values as MutableMap<*, *>
                        bookedSeatlist.add(
                            BookedSeat(
                                seat["Name"] as String,
                                seat["Phone No."] as String,
                                seat["Email"] as String,
                                seat["Number Of Seats"] as String,
                                seat["Selected Seats"] as String,
                                seat["Total Price"] as String,
                            )
                        )
                    }
                }
                progressBar.visibility=View.GONE
                bookedSeatAdapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
    }

}