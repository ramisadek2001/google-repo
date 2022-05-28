package com.lau.google_rep.UI

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
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = MainAdapter()
    var currentPage = 0
    val TOTAL_PAGES = 5
    var isLoading = false
    var isLastPage = false
    var i: Int = 0
    var totalitems: Int = 3
    val response = MainRepository(retrofitService).getAllrepos()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)

        binding.recyclerview.adapter = adapter

        val linearLayoutManager : LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = linearLayoutManager


        

        binding.recyclerview.addOnScrollListener(object: PaginationScrollListener(linearLayoutManager){
            override fun loadMoreItems() {
                currentPage += 1
                totalitems += 3
                loadNextPage()

            }

            override var isLastPage: Boolean = this@MainActivity.isLastPage

            override var isLoading: Boolean = this@MainActivity.isLoading


        })



//            viewModel.repoList.observe(this, Observer {
//
//                Log.d(TAG, "onCreate: $it")
//                adapter.setRepoList(it)
//
//            })
//            viewModel.errorMessage.observe(this, Observer {
//            })
       loadFirstPage()


    }
    fun loadNextPage() {
        Log.e(TAG, "loadNextPage: $totalitems $i", )

        response.clone().enqueue(object : Callback<List<repo>> {

            override fun onResponse(call: Call<List<repo>>, response: Response<List<repo>>) {

                adapter.removeLoadingFooter()
                isLoading = false
                val results = response.body()
                if (results != null) {
                    while (i<totalitems){
                        adapter.add(results[i])

                        i++
                    }



                }
                if (currentPage !== TOTAL_PAGES) adapter.addLoadingFooter() else isLastPage =
                    true
            }

            override fun onFailure(call: Call<List<repo>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    fun loadFirstPage(){

        response.enqueue(object : Callback<List<repo>> {

            override fun onResponse(call: Call<List<repo>>, response: Response<List<repo>>) {

                val results: List<repo>? = response.body()
                binding.progressbar.setVisibility(View.GONE)
                if (results != null) {

                    while (i<totalitems){
                        adapter.add(results[i])
                        i++

                    }



                }

                if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter() else isLastPage =
                    true
            }

            override fun onFailure(call: Call<List<repo>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}