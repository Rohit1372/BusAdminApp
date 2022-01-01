package com.example.android.busadminapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context:Context,val userList:ArrayList<UserData>):RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    inner class UserViewHolder(val v: View):RecyclerView.ViewHolder(v){
        val from = v.findViewById<TextView>(R.id.from_list_item)
        val arrive = v.findViewById<TextView>(R.id.arrive_list_item)
        val serv = v.findViewById<TextView>(R.id.busService_list_item)
        val busNo = v.findViewById<TextView>(R.id.busNumber_list_item)
        val date = v.findViewById<TextView>(R.id.date_list_item)
        val startTime = v.findViewById<TextView>(R.id.time1_list_item)
        val arriveTime = v.findViewById<TextView>(R.id.time3_list_item)
        val price = v.findViewById<TextView>(R.id.rate_list_item)
        val viewRoutes = v.findViewById<TextView>(R.id.viewRoutes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.from.text = newList.from
        holder.arrive.text = newList.arrive
        holder.serv.text  = newList.service
        holder.busNo.text = newList.busNo
        holder.date.text = newList.date
        holder.startTime.text = newList.startTime
        holder.arriveTime.text = newList.arrivalTime
        holder.price.text = newList.rate
        holder.viewRoutes.setOnClickListener {
            val intent = Intent(context,MainActivity2::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

}