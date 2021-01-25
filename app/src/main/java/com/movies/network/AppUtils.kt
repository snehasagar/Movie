package com.movies.network

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.movies.R

object AppUtils {

    fun ImageView.loadImage(url: String?) {
        val options = RequestOptions()
            //.placeholder(getProgressDrawable(context))
            .error(R.drawable.ic_launcher_background)
        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter("android:imageUrl")
    fun androidImageUrl(view: ImageView, url: String?) {
        view.loadImage(url)
    }


}