package com.lau.google_rep.repository

import com.lau.google_rep.data.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllrepos() = retrofitService.getAllrepos()
}