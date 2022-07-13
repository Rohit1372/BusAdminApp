package com.example.android.busadminapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.model.BookedSeat

class BookedSeatAdapter(val context:Context,val bookedList : ArrayList<BookedSeat>):RecyclerView.Adapter<BookedSeatAdapter.BookedSeatAdapterViewHolder>(){

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

}