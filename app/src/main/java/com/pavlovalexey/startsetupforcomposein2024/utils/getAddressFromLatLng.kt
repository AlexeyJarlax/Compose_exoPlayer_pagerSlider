package com.pavlovalexey.startsetupforcomposein2024.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.pavlovalexey.startsetupforcomposein2024.R
import java.util.Locale

fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String? {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            val address: Address = addresses[0]
            address.getAddressLine(0)
        } else {
            ""
        }
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}