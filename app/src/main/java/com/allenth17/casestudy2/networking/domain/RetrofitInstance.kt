package com.allenth17.casestudy2.networking.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://dummyjson.com/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiInterface: ApiInterface = getInstance().create(ApiInterface::class.java)

    // Helper to build an absolute icon URL for Coil or other clients.
    fun buildIconUrl(username: String, size: Int = 128): String =
        BASE_URL + "icon/" + username + "/" + size
}