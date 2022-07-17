package com.example.githubdemo.network

import com.example.githubdemo.models.PrDetails
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("Be-Better-Programmer/Tic-Tac-Toe/pulls?state=closed")
    fun getClosedPr() : Call<List<PrDetails>>

    companion object {
        var retrofitService: RetrofitService? = null
        private const val API_BASE_URL = "https://api.github.com/repos/"

        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val httpClient = OkHttpClient.Builder()
                httpClient.addInterceptor(logging)

                val retrofit = Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}