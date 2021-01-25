package com.movies

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions.with
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.movies.databinding.ItemMovielistLayoutBinding
import com.squareup.picasso.Picasso
import org.jetbrains.anko.sdk27.coroutines.onClick

class MovieListAdapter(val context: Context?,private val arrayList: ArrayList<MovieViewModel>,
                       private val listener: (MovieViewModel, Int) -> Unit)
    : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val packageBinding: ItemMovielistLayoutBinding= DataBindingUtil.inflate(layoutInflater, R.layout.item_movielist_layout, parent, false)
        return ViewHolder(packageBinding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(context,arrayList[position], position, listener)
    }

    class ViewHolder(private val packageBinding: ItemMovielistLayoutBinding) : RecyclerView.ViewHolder(packageBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bindItem(context: Context?, viewModel: MovieViewModel, position: Int, listener:(MovieViewModel, Int) -> Unit) {
            this.packageBinding.data = viewModel
            context?.let {
                with(it).load(viewModel.getTnC.Poster).into(packageBinding.imageView2) }
            packageBinding.textView6.text = "Title : " + viewModel.getTnC.Title
            packageBinding.textView5.text = "Year : " + viewModel.getTnC.Year

            packageBinding.executePendingBindings()
            itemView.onClick {
                listener(viewModel, position)
            }
        }
    }
}