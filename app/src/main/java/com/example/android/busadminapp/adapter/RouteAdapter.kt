package com.example.android.busadminapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.R
import com.example.android.busadminapp.model.Route
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RouteAdapter( private val context : Context,private val routeList: ArrayList<Route>,val id:String) : RecyclerView.Adapter<RouteAdapter.RouteAdapterViewHolder>() {

    class RouteAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
        val stopNo :TextView = view.findViewById(R.id.stopNo_list_item)
        val stopAt :TextView = view.findViewById(R.id.stopAt_list_item)
        val stopTime :TextView = view.findViewById(R.id.stopTime_list_item)
        val delete : ImageView = view.findViewById(R.id.delete_route_list)
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

        //delete routes from list
        holder.delete.setOnClickListener {

            val route: MutableMap<String, Any> = HashMap()

            route["Stop Number"] = currentRoute.stopNumber
            route["Stop At"] = currentRoute.stopAt
            route["Stop Time"] = currentRoute.stopTime

            val db = Firebase.firestore

            db.collection("Buses").document(id).update("Routes",FieldValue.arrayRemove(route))
                .addOnSuccessListener {
                    routeList.remove(currentRoute)
                    Toast.makeText(context,"Route is deleted",Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to delete route",Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun getItemCount(): Int {
    return routeList.size
    }
}