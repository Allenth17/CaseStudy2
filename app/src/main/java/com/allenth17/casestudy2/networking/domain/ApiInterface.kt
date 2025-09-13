package com.allenth17.casestudy2.networking.domain

import com.allenth17.casestudy2.networking.UsersResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("users")
    suspend fun getUsers(): Response<UsersResponse>

    @GET("icon/{username}/{size}")
    suspend fun getIcon(
        @Path("username") username: String,
        @Path("size") size: Int = 128
    ): Response<ResponseBody>
}