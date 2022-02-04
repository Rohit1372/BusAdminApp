package com.example.android.busadminapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android.busadminapp.activity.Routes
import com.example.android.busadminapp.R
//import com.example.android.busadminapp.activity.Buses
import com.example.android.busadminapp.model.Bus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BusAdapter(private val context:Context, private val busList:ArrayList<Bus>):RecyclerView.Adapter<BusAdapter.BusAdapterViewHolder>(),Filterable{

    var countryFilterList : ArrayList<Bus>

    init {
        countryFilterList = ArrayList()
        countryFilterList.addAll(busList)
    }

    inner class BusAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
        val from :TextView = view.findViewById(R.id.from_list_item)
        val to:TextView  = view.findViewById(R.id.to_list_item)
        val busService:TextView  = view.findViewById(R.id.busService_list_item)
        val busNo:TextView  = view.findViewById(R.id.busNumber_list_item)
        val date:TextView  = view.findViewById(R.id.date_list_item)
        val startTime:TextView  = view.findViewById(R.id.start_time_list_item)
        val arrivalTime:TextView  = view.findViewById(R.id.arrival_time_list_item)
        val rate:TextView  = view.findViewById(R.id.rate_list_item)
        val delete : ImageView = view.findViewById(R.id.delete_list_item)
        val viewRoutes:TextView  = view.findViewById(R.id.viewRoutes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.bus_single_item_view,parent,false)
        return BusAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusAdapterViewHolder, position: Int) {
        //val currentBus = busList[position]
        val currentBus = countryFilterList[position]
        holder.from.text = currentBus.from
        holder.to.text = currentBus.to
        holder.busService.text  = currentBus.service
        holder.busNo.text = currentBus.busNo
        holder.date.text = currentBus.date
        holder.startTime.text = currentBus.startTime
        holder.arrivalTime.text = currentBus.arrivalTime
        holder.rate.text = currentBus.price
        holder.viewRoutes.setOnClickListener {
            val intent = Intent(context, Routes::class.java)
                .putExtra("id",currentBus.id)
                .putExtra("from",holder.from.text)
                .putExtra("to",holder.to.text)
                .putExtra("bus service",holder.busService.text)
                .putExtra("bus no.",holder.busNo.text)
                .putExtra("date",holder.date.text)
                .putExtra("start time",holder.startTime.text)
                .putExtra("arrival time",holder.arrivalTime.text)
            context.startActivity(intent)
        }


        //Delete Bus item from firestore

        holder.delete.setOnClickListener {
            val db = Firebase.firestore
            db.collection("Buses").document(currentBus.id)
                .delete()
                .addOnSuccessListener {
                    busList.remove(currentBus)
                    Toast.makeText(context,"Data is Deleted",Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }.addOnFailureListener {
                    Toast.makeText(context,"Failed to delete data",Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun getItemCount(): Int {
        //return busList.size
        return  countryFilterList.size
    }


    //Filter data

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryFilterList = busList as ArrayList<Bus>
                } else {
                    val resultList = ArrayList<Bus>()
                    for (row in busList) {
                        if (row.from.toLowerCase().contains(constraint.toString().toLowerCase())
                            || row.to.toLowerCase().contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryFilterList = results?.values as ArrayList<Bus>
                notifyDataSetChanged()
            }
        }
    }

}