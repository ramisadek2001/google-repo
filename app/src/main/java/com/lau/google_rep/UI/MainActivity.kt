package com.lau.google_rep.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lau.google_rep.R
import com.lau.google_rep.ViewModel.MainAdapter
import com.lau.google_rep.ViewModel.MainViewModel
import com.lau.google_rep.ViewModel.PaginationScrollListener
import com.lau.google_rep.ViewModel.ViewModelFactory
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.data.repo
import com.lau.google_rep.databinding.ActivityMainBinding
import com.lau.google_rep.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates
import android.graphics.Movie




class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        val context : Context = this.applicationContext

        viewModel.initializeRecycler(binding,context)


    }
}