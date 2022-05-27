package com.lau.google_rep.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lau.google_rep.data.repo
import com.lau.google_rep.databinding.RepoLayoutBinding
import android.R
import android.graphics.Insets.add
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView

import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import android.graphics.Movie





class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val LOADING = 0
    private val ITEM = 1
    private var isLoadingAdded = false

    var repos = mutableListOf<repo>()

    fun setRepoList(repos: List<repo>) {
        this.repos = repos.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val viewItem: View =
                    inflater.inflate(com.lau.google_rep.R.layout.repo_layout, parent, false)
                viewHolder = MainViewHolder(viewItem)
            }
            LOADING -> {
                val viewLoading: View =
                    inflater.inflate(com.lau.google_rep.R.layout.loading, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }


        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val mainViewHolder: MainViewHolder = holder as MainViewHolder
        val repo = repos[position]

        when (getItemViewType(position)) {
            ITEM -> {
                mainViewHolder.Title?.text = repo.name
                mainViewHolder.Image?.let {
                    Glide.with(holder.itemView.context).load(repo.owner.avatar_url).into(
                        it
                    )
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (position == repos.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(repo())
    }
    fun add(repo: repo) {
        repos.add(repo)
        notifyItemInserted(repos.size - 1)
    }

    fun addAll(repoResults: List<repo>) {
        for (result in repoResults) {
            add(result)
        }
    }

    fun getItem(position: Int): repo {
        return repos.get(position)
    }
    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = repos.size - 1
        val result: repo = getItem(position)
        if (result != null) {
            repos.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Title: TextView? =
            itemView.findViewById<View>(com.lau.google_rep.R.id.title) as TextView
        var Image: ImageView? =
            itemView.findViewById<View>(com.lau.google_rep.R.id.imgCircle) as ImageView

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(com.lau.google_rep.R.id.progressbar)
        }
    }
}