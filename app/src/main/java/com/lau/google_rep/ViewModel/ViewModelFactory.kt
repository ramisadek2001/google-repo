package com.lau.google_rep.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.repository.PostDataSource

class ViewModelFactory constructor(private val apiService: RetrofitService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
             return MainViewModel(apiService) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}