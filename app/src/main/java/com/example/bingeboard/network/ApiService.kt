package com.example.bingeboard.network

import com.example.bingeboard.network.models.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getAllMovies(@Query("api_key") apiKey: String): Response<ApiResponse>

}