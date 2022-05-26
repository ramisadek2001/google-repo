package com.lau.google_rep.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lau.google_rep.R
import com.lau.google_rep.ViewModel.MainAdapter
import com.lau.google_rep.ViewModel.MainViewModel
import com.lau.google_rep.ViewModel.ViewModelFactory
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.databinding.ActivityMainBinding
import com.lau.google_rep.repository.MainRepository

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(this,2)
        viewModel.repoList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setRepoList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        viewModel.getAllrepos()
    }
}