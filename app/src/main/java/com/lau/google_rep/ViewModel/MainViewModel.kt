package com.lau.google_rep.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lau.google_rep.data.repo
import com.lau.google_rep.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lau.google_rep.R
import com.lau.google_rep.UI.MainActivity
import com.lau.google_rep.databinding.ActivityMainBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.zip.Inflater


 class MainViewModel  constructor(private val repository: MainRepository)  : ViewModel() {

    val repoList = MutableLiveData<List<repo>>()
    val errorMessage = MutableLiveData<String>()
//    val adapter = MainAdapter()
    val adapter = MainAdapter()

     var currentPage = 0
     val TOTAL_PAGES = 5
     var isLoading = false
     var isLastPage = false
     var i: Int = 0
     var mainActivity: MainActivity = MainActivity()

    lateinit var binding: ActivityMainBinding
     var totalitems: Int = 3
     val response = repository.getAllrepos()



     fun initializeRecycler(binding: ActivityMainBinding, context: Context){
         this.binding = binding

         binding.recyclerview.adapter = adapter


         val gridLayoutManager : GridLayoutManager = GridLayoutManager(context,2)
         binding.recyclerview.layoutManager = gridLayoutManager

         binding.recyclerview.addOnScrollListener(object: PaginationScrollListener(gridLayoutManager){
             override fun loadMoreItems() {
                 currentPage += 1


                 loadNextPage()
             }

             override var isLastPage: Boolean = this@MainViewModel.isLastPage

             override var isLoading: Boolean = this@MainViewModel.isLoading


         })
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
                     while (i<totalitems && totalitems<= results.size + 1){

                         adapter.add(results[i])
                         i++

                     }
                     if (totalitems < results.size){
                         totalitems += 3
                     }
                     if (totalitems.equals(results.size+1)){
                     binding.progressbar.setVisibility(View.GONE)
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
                     totalitems += 3


                 }

                 if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter() else isLastPage =
                     true
             }

             override fun onFailure(call: Call<List<repo>>, t: Throwable) {
                 t.printStackTrace()
             }
         })
     }


//    fun getAllrepos() {
//        val response = repository.getAllrepos()
//
//        response.enqueue(object : Callback<List<repo>> {
//
//            override fun onResponse(call: Call<List<repo>>, response: Response<List<repo>>) {
//                repoList.postValue(response.body())
//            }
//            override fun onFailure(call: Call<List<repo>>, t: Throwable) {
//                errorMessage.postValue(t.message)
//            }
//        })
//    }
}