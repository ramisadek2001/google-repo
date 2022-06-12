package com.lau.google_rep.ViewModel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lau.google_rep.data.repo
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
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.repository.PostDataSource


class MainViewModel(private val apiService: RetrofitService) : ViewModel() {
    val listData = Pager(PagingConfig(pageSize = 6)) {
        PostDataSource(apiService)
    }.flow.cachedIn(viewModelScope)

}
//    fun initializeRecycler(binding: FragmentMainBinding, context: Context) {
//        this.binding = binding
//        handler = Handler()
//
//
//
//        adapter.setOnItemClickListener(object : MainAdapter.onItemClickListener {
//            override fun OnItemClick(position: Int) {
//                val intent = Intent(context, repo_item::class.java)
//
//                repoArrayList = repoList.value as ArrayList<repo>
//                intent.putExtra("name", repoArrayList[position].name)
//                intent.putExtra("avatar_url", repoArrayList[position].owner.avatar_url)
//                intent.putExtra("created_at", repoArrayList[position].created_at)
//                intent.putExtra("stargazers_count", repoArrayList[position].stargazers_count)
//
//                startActivity(context, intent, null)
//
//            }
//
//        })
//
//

//
//        binding.include.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//                adapter.removeLoadingSearchFooter()
//                adapter.filter.filter(p0)
//                if (p0 == "") {
//                    val intent = Intent(context, MainActivity::class.java)
//                    startActivity(context, intent, null)
//                }
//                return false
//            }
//
//        })
//
//
//    }

