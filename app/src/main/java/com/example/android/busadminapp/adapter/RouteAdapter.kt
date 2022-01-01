package com.example.android.busadminapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.model.Route

class RouteAdapter( private val routeList: ArrayList<Route>) : RecyclerView.Adapter<RouteAdapter.RouteAdapterViewHolder>() {

    class RouteAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
        val stopNo :TextView = view.findViewById(R.id.stopNo_list_item)
        val stopAt :TextView = view.findViewById(R.id.stopAt_list_item)
        val stopTime :TextView = view.findViewById(R.id.stopTime_list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.route_single_item_view,parent,false)
        return RouteAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: RouteAdapterViewHolder, position: Int) {
        val currentRoute = routeList[position]

        holder.stopAt.text=currentRoute.stopAt
        holder.stopNo.text=currentRoute.stopNumber
        holder.stopTime.text=currentRoute.stopTime
    }

    override fun getItemCount(): Int {
    return routeList.size
    }
}