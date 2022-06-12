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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates
import android.graphics.Movie
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lau.google_rep.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

//    lateinit var viewModel: MainViewModel
//    private val retrofitService = RetrofitService.getInstance()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
//
//        val context : Context = this.applicationContext
//
//        viewModel.initializeRecycler(binding,context)

        val firstFragment=com.lau.google_rep.UI.Fragments.MainFragment()
        val secondFragment= com.lau.google_rep.UI.Fragments.InfoFragment()

        setCurrentFragment(firstFragment)
        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.list -> setCurrentFragment(firstFragment)
                R.id.Information -> {
                    setCurrentFragment(secondFragment)
                }
            }
            true
        }

    }

    private fun setCurrentFragment(fragment:Fragment) {
        if (fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,fragment)
            transaction.commit()
        }
    }

}