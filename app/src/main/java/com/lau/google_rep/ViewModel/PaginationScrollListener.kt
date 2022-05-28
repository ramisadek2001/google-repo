package com.lau.google_rep.ViewModel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.LinearLayoutManager


abstract class PaginationScrollListener(private val layoutManager: GridLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        Log.e(TAG, "onScrolled: $isLoading $isLastPage", )
        if (!isLoading && !isLastPage) {


                loadMoreItems()

        }
    }

    protected abstract fun loadMoreItems()
    abstract var isLastPage: Boolean
    abstract var isLoading: Boolean

}