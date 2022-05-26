package com.lau.google_rep.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lau.google_rep.data.repo
import com.lau.google_rep.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel  constructor(private val repository: MainRepository)  : ViewModel() {

    val repoList = MutableLiveData<List<repo>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllrepos() {
        val response = repository.getAllrepos()

        response.enqueue(object : Callback<List<repo>> {

            override fun onResponse(call: Call<List<repo>>, response: Response<List<repo>>) {
                repoList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<repo>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }
}