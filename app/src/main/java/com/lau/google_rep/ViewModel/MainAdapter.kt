package com.lau.google_rep.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lau.google_rep.data.repo
import android.R
import android.graphics.Insets.add
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*

import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.NonDisposableHandle.parent


class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() , Filterable {

    private val LOADING = 0
    private val ITEM = 1
    public var isLoadingAdded = true
    lateinit var viewLoading: View

    private lateinit var   mlistener: onItemClickListener
    interface  onItemClickListener{
        fun OnItemClick(position: Int)
    }

    var repos: MutableList<repo> = ArrayList()
    private var reposFull : MutableList<repo> = ArrayList()

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
                viewHolder = MainViewHolder(viewItem, mlistener)
            }
            LOADING -> {
                 viewLoading= inflater.inflate(com.lau.google_rep.R.layout.loading, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
        }


        return viewHolder!!
    }

    fun setOnItemClickListener(listener : onItemClickListener){

        mlistener = listener

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        val repo = repos[position]

        when (getItemViewType(position)) {
            ITEM -> {
                val mainViewHolder: MainViewHolder = holder as MainViewHolder
                mainViewHolder.Title?.text = repo.name
                mainViewHolder.Image?.let {
                    Glide.with(holder.itemView.context).load(repo.owner.avatar_url).into(
                        it
                    )
                }
            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                if(!isLoadingAdded){
                   loadingViewHolder.progressBar.visibility = View.GONE
                }


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
        reposFull.add(repo)
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

    class MainViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var Title: TextView? =
            itemView.findViewById<View>(com.lau.google_rep.R.id.title) as TextView
        var Image: ImageView? =
            itemView.findViewById<View>(com.lau.google_rep.R.id.imgCircle) as ImageView

        init {
            itemView.setOnClickListener {
                listener.OnItemClick(adapterPosition)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar

        init {
            progressBar = itemView.findViewById(com.lau.google_rep.R.id.progressBar2)!!
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }
    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: MutableList<repo> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(reposFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in reposFull) {
                    if (item.name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults) {
            repos.clear()
            repos.addAll(results.values as Collection<repo>)
            notifyDataSetChanged()
        }
    }
}