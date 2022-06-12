package com.lau.google_rep.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.lau.google_rep.R
import com.lau.google_rep.databinding.LoadingBinding

class LoadStateViewHolder(parent: ViewGroup, retry:() -> Unit
): RecyclerView.ViewHolder(

    LayoutInflater.from(parent.context)
        .inflate(R.layout.loading,parent, false)
) {
    private val binding = LoadingBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progressBar2

    fun bind(loadState: LoadState) {


        progressBar.isVisible = loadState is LoadState.Loading

    }
}