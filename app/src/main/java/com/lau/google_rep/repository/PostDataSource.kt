package com.lau.google_rep.repository

import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lau.google_rep.data.RetrofitService
import com.lau.google_rep.data.repo
import retrofit2.HttpException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class PostDataSource(private val apiService: RetrofitService) : PagingSource<Int, repo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, repo> {

        try {
            val currentLoadingPageKey = params.key ?: 1
            val response  = apiService.getListData(currentLoadingPageKey)
            val responseData = mutableListOf<repo>()

            var repos : List<repo>


            val data = response
            responseData.addAll(data)
            Log.e(TAG, "load: $responseData", )
            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = currentLoadingPageKey.plus(1)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)

        }
    }
    override fun getRefreshKey(state: PagingState<Int, repo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


//    fun getAllrepos() = retrofitService.getAllrepos()


}