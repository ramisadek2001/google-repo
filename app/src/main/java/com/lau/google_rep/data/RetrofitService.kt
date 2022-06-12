package com.lau.google_rep.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

//   @Headers("Authorization: token ")
    @GET("repos")
    suspend fun getListData(@Query("page") pageNumber: Int): List<repo>

    companion object{
        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun getApiService() = Retrofit.Builder()
            .baseUrl("https://api.github.com/orgs/google/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(RetrofitService::class.java)

    }
}