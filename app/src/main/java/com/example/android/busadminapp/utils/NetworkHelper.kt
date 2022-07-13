package com.example.android.busadminapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build

object NetworkHelper {

    fun isNetworkConnected(context : Context) : Boolean{
        var results = false
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                results = checkNetworkConnection(this,this.activeNetwork)
            }else{
                val networks = this.allNetworks
                for (network in networks){
                    if(checkNetworkConnection(this,network)){
                        results=true
                    }
                }
            }
        }
        return results
    }

    private fun checkNetworkConnection(
        connectivityManager: ConnectivityManager,
        network: Network?
    ) : Boolean{
        connectivityManager.getNetworkCapabilities(network)?.also {
            if(it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return true
            } else if(it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                return true
            }
        }
        return false
    }

}