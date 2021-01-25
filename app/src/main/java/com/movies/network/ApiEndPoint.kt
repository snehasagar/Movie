package com.movies.network

import com.movies.model.MovieDetails
import com.movies.model.MovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiEndPoint {

    @GET("/?")
    fun getMovieList(
        @Query("apikey") apikey:String,
        @Query("s") s:String,
        @Query("type") type: String
    ): Call<MovieList.Response>

    @GET("/?")
    fun getMovieDetails(
        @Query("apikey") apikey:String,
        @Query("i") i:String
    ): Call<MovieDetails.Response>


}