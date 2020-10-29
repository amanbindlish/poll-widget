package com.bindlish.pollwidget

import android.content.Context
import com.bindlish.pollwidget.data.PollData
import com.google.gson.Gson
import java.io.IOException

fun loadJSONFromAssets(context : Context?): String {
    var json = ""
    try {
        context?.apply {
            val inputStream = this.assets.open("poll_data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return json
}

fun saveJsonToPreference(jsonString : String, context: Context?) {
    context?.apply {
        this.getSharedPreferences("poll_widget", Context.MODE_PRIVATE)
            .edit().putString("key_poll_data", jsonString).apply()
    }
}

fun getPollObjectFromPreference(context: Context?) : PollData {
    context?.apply {
        val jsonString = this.getSharedPreferences("poll_widget", Context.MODE_PRIVATE)
            .getString("key_poll_data", loadJSONFromAssets(context))
        return Gson().fromJson(jsonString, PollData::class.java)
    }
    return Gson().fromJson(loadJSONFromAssets(context), PollData::class.java)
}

