package com.lau.google_rep.ViewModel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lau.google_rep.data.repo
import com.lau.google_rep.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.graphics.Movie
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lau.google_rep.R
import com.lau.google_rep.UI.MainActivity
import com.lau.google_rep.UI.repo_item
import com.lau.google_rep.databinding.ActivityMainBinding
import com.lau.google_rep.databinding.FragmentMainBinding
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.zip.Inflater
import android.widget.ProgressBar


class MainViewModel  constructor(private val repository: MainRepository)  : ViewModel() {

    val repoList = MutableLiveData<List<repo>>()
     var repoArrayList = ArrayList<repo>()
    val errorMessage = MutableLiveData<String>()
//    val adapter = MainAdapter()
    val adapter = MainAdapter()

     var currentPage = 0
     var TOTAL_PAGES = 5
     var isLoading = false
     var isLastPage = false
     lateinit var handler: Handler
     var i: Int = 0
     lateinit var binding: FragmentMainBinding
     var totalitems: Int = 3
     val response = repository.getAllrepos()
     lateinit var context : Context



     fun initializeRecycler(binding: FragmentMainBinding, context: Context ){
         this.binding = binding
         handler = Handler()

         binding.recyclerview.adapter = adapter
         this.context = context


         val gridLayoutManager : GridLayoutManager = GridLayoutManager(context,2)
         binding.recyclerview.layoutManager = gridLayoutManager

         binding.recyclerview.addOnScrollListener(object: PaginationScrollListener(gridLayoutManager){
             override fun loadMoreItems() {
                 currentPage += 1
                 handler.postDelayed({
                     loadNextPage()
                 },2000)
             }

             override var isLastPage: Boolean = this@MainViewModel.isLastPage

             override var isLoading: Boolean = this@MainViewModel.isLoading
         })
         loadFirstPage()

         adapter.setOnItemClickListener(object : MainAdapter.onItemClickListener{
             override fun OnItemClick(position: Int) {
                 val intent = Intent(context,repo_item::class.java)

                 repoArrayList = repoList.value as ArrayList<repo>
                 intent.putExtra("name",repoArrayList[position].name)
                 intent.putExtra("avatar_url",repoArrayList[position].owner.avatar_url)
                 intent.putExtra("created_at",repoArrayList[position].created_at)
                 intent.putExtra("stargazers_count",repoArrayList[position].stargazers_count)

                 startActivity(context,intent,null)

             }

         })




         binding.include.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
             override fun onQueryTextSubmit(p0: String?): Boolean {
                 return false
             }

             override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                 return false
             }

         })



     }

     fun loadNextPage() {
         Log.e(TAG, "loadNextPage: $totalitems $i")

         response.clone().enqueue(object : Callback<List<repo>> {

             override fun onResponse(call: Call<List<repo>>, response: Response<List<repo>>) {
//
//                 adapter.removeLoadingFooter()
                 isLoading = false
                 adapter.isLoadingAdded = false
                 val inflatedView: View = View.inflate(context, com.lau.google_rep.R.layout.loading,null)

                 val progressBar : ProgressBar = inflatedView.findViewById(com.lau.google_rep.R.id.progressBar2)
                 progressBar.visibility = View.GONE



                 val results = response.body()
                 if (results != null) {

                     while (i<totalitems && totalitems<= results.size){

                         val res = results[i]
                         Log.e(TAG, "onResponse: $res")
                         adapter.add(results[i])

                         i++
                     }
                     if (totalitems < results.size + 3){
                         totalitems += 3
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
                     TOTAL_PAGES = (results.size/4) +1
                     repoList.postValue(results)
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