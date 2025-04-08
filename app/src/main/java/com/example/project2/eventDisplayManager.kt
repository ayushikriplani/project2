package com.example.project2

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class eventDisplayManager {
    val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }

    val apiKey = "kONZlxcGmOsPJfTCm71yisQY5wjhkVfz"

    suspend fun retrieveEveryEvent(apiKey: String): List<event> {
        val url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=$apiKey"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val eventsList = mutableListOf<event>()
            val json = JSONObject(responseBody)

            if (json.has("_embedded")) {
                val embedded = json.getJSONObject("_embedded")
                val events = embedded.getJSONArray("events")
                for (i in 0..events.length()) {
                    val event = events.getJSONObject(i)
                    val title = event.getString("title")
                    val url = event.getString("url")
                    val image = event.getString("urlToImage")
                    eventsList.add(event(title, url, image))
                }
            }

            return eventsList
        } else {
            return listOf()
        }
    }
}
