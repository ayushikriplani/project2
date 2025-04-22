package com.example.project2

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject

class eventDisplayManager {
    private val okHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        okHttpClient = builder.build()
    }

    suspend fun retrieveEveryEvent(apiKey: String, postalCode: String): List<event> {
        val url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=$apiKey&postalCode=$postalCode"
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        val responseBody = response.body?.string()
        val eventsList = mutableListOf<event>()

        if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
            val json = JSONObject(responseBody)

            if (json.has("_embedded")) {
                val events = json.getJSONObject("_embedded").getJSONArray("events")
                for (i in 0..<events.length()) {
                    val eventObj = events.getJSONObject(i)
                    val name = eventObj.getString("name")
                    val eventUrl = eventObj.optString("url", "")
                    //Ticketmaster API uses an image array:https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/#anchor_getImages
                    //Check to see if the array is empty and if it is, set the url to an empty string
                    val imagesArray = eventObj.optJSONArray("images")
                    val urlToImage = if (imagesArray != null && imagesArray.length() > 0) {
                        imagesArray.getJSONObject(0).optString("url", "")
                        } else {
                        ""
                    }
                    eventsList.add(event(name, eventUrl, urlToImage))
                }
            }
        }
        return eventsList
    }
}
