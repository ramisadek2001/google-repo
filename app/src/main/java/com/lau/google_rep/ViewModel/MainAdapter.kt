package com.lau.google_rep.ViewModel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lau.google_rep.data.repo
import com.lau.google_rep.databinding.RepoLayoutBinding

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var repos = mutableListOf<repo>()

    fun setRepoList(repos: List<repo>) {
        this.repos = repos.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepoLayoutBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.title.text = repo.name
        Glide.with(holder.itemView.context).load(repo.owner.avatar_url).into(holder.binding.imgCircle)
    }
    override fun getItemCount(): Int {
        return repos.size
    }
}
class MainViewHolder(val binding: RepoLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
}