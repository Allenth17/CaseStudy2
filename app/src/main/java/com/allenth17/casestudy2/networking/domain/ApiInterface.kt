package com.allenth17.casestudy2.networking.domain

import com.allenth17.casestudy2.networking.UsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("users")
    suspend fun getUsers(): Response<UsersResponse>
}