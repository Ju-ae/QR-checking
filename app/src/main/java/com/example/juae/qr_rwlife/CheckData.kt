package com.example.juae.qr_rwlife

import java.sql.Timestamp
import java.util.*
import android.R.attr.country
import android.R.attr.name



data class CheckData(var name: String, var group : String, var uid : String, var ismale : Boolean, var timestamp: Date)
{
 //   fun CheckData(name: String, group: String, uid: String, ismale: Boolean, timestamp: Timestamp){
  //  }
/*
    fun getName(): String {
        return name
    }

    fun getState(): String {
        return state
    }

    fun getCountry(): String {
        return country
    }

    fun isCapital(): Boolean {
        return capital
    }

    fun getPopulation(): Long {
        return population
    }*/

}