package com.pavlovalexey.startsetupforcomposein2024.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.IOException
import androidx.annotation.Keep

@Keep
suspend fun loadImageUrlsFromAssets(context: android.content.Context): List<String> = withContext(Dispatchers.IO) {
    val urls = mutableListOf<String>()
    try {
        val inputStream = context.assets.open("image_urls.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            urls.add(jsonArray.getString(i))
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    urls
}