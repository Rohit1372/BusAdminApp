package com.example.android.busadminapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.model.BookedSeat
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BookedSeatAdapter(val context:Context,val bookedList : ArrayList<BookedSeat>,var id : String):RecyclerView.Adapter<BookedSeatAdapter.BookedSeatAdapterViewHolder>(){

    class BookedSeatAdapterViewHolder(view:View):RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.name_text)
        val phoneNo : TextView = view.findViewById(R.id.phone_text)
        val email : TextView = view.findViewById(R.id.email_text)
        val noOfSeats : TextView = view.findViewById(R.id.noOfSeats_text)
        val selectedSeats : TextView = view.findViewById(R.id.selected_seats_text)
        val totalPrice : TextView = view.findViewById(R.id.totalPrice_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookedSeatAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.booked_seat_single_item_view,parent,false)
        return BookedSeatAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookedSeatAdapterViewHolder, position: Int) {
        val list = bookedList[position]
        holder.name.text = list.name
        holder.phoneNo.text = list.phoneNo
        holder.email.text = list.email
        holder.noOfSeats.text = list.noOfSeats
        holder.selectedSeats.text = list.selectedSeats
        holder.totalPrice.text = list.totalPrice
    }

    override fun getItemCount(): Int {
        return bookedList.size
    }

    fun deleteItem(position: Int){
        val bookedSeat: MutableMap<String, Any> = HashMap()
        val list = bookedList[position]

        bookedSeat["Name"] = list.name
        bookedSeat["Phone No."] = list.phoneNo
        bookedSeat["Email"] = list.email
        bookedSeat["Number Of Seats"] = list.noOfSeats
        bookedSeat["Selected Seats"] = list.selectedSeats
        bookedSeat["Total Price"] = list.totalPrice

        val db = Firebase.firestore

        db.collection("Buses").document(id).update("BookedSeats", FieldValue.arrayRemove(bookedSeat))
            .addOnSuccessListener {
                bookedList.remove(list)
                Toast.makeText(context,"Booked seats Deleted Successfully", Toast.LENGTH_LONG).show()
                notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(context,"Failed to delete data", Toast.LENGTH_LONG).show()
            }
    }

}