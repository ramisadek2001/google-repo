package com.lau.google_rep.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lau.google_rep.data.repo
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import android.widget.*
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.lau.google_rep.R


class MainAdapter : PagingDataAdapter<repo, MainAdapter.ViewHolder>(DiffCallBack) , Filterable {

    private lateinit var Repos : List<repo>

    class ViewHolder(view: View, mlistener: onItemClickListener) : RecyclerView.ViewHolder(view)

        private lateinit var mlistener: onItemClickListener

        interface onItemClickListener {
            fun OnItemClick(position: Int)
        }

        var repos: MutableList<repo> = ArrayList()
        private var reposFull: MutableList<repo> = ArrayList()


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.itemView.findViewById<TextView>(R.id.title).text = getItem(position)?.name
            Log.e(TAG,"name ${holder.itemView.findViewById<TextView>(R.id.title).text}", )

            Glide.with(holder.itemView.context)
                .load(getItem(position)?.owner?.avatar_url)
                .into(holder.itemView.findViewById(R.id.imgCircle))

//        val repo = repos[position]
//
//
//        val mainViewHolder: ViewHolder = holder as ViewHolder
//        mainViewHolder.itemView Title?.text = repo.name
//                mainViewHolder.Image?.let {
//                    Glide.with(holder.itemView.context).load(repo.owner.avatar_url).into(
//                        it
//                    )
//                }
        }

    fun submitList(list: PagingData<repo>) {
        Repos = list as List<repo>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repo_layout, parent, false), mlistener)
        }

        fun setOnItemClickListener(listener: onItemClickListener) {

            mlistener = listener

        }


        object DiffCallBack : DiffUtil.ItemCallback<repo>() {
            override fun areItemsTheSame(oldItem: repo, newItem: repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: repo, newItem: repo): Boolean {
                return oldItem == newItem
            }

        }


//    class MainViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        var Title: TextView? =
//            itemView.findViewById<View>(com.lau.google_rep.R.id.title) as TextView
//        var Image: ImageView? =
//            itemView.findViewById<View>(com.lau.google_rep.R.id.imgCircle) as ImageView
//
//        init {
//            itemView.setOnClickListener {
//                listener.OnItemClick(adapterPosition)
//            }
//        }
//    }


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
                Log.d(TAG, "publishResults: $filteredList")
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                repos.clear()




                (results.values as? Collection<repo>)?.let { repos.addAll(it) }



                notifyDataSetChanged()
            }
        }


    }
