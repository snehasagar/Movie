@file:Suppress("DEPRECATION")

package com.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_detail_layout.*


@Suppress("DEPRECATION")
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail_layout)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        var id = intent.getStringExtra("id")
        movieViewModel.makeMovieDetailRequest(id)
        movieViewModel.title.observe(this, Observer {
            txt_title.text = movieViewModel.title.value
        })
        movieViewModel.poster.observe(this, Observer {
            let { Glide.with(it).load(movieViewModel.poster.value).into(img_poster) }
        })
        movieViewModel.year.observe(this, Observer {
            txt_year.text = movieViewModel.year.value
        })
        movieViewModel.director.observe(this, Observer {
            textView10.text = movieViewModel.director.value
        })
        movieViewModel.writer.observe(this, Observer {
            textView12.text = movieViewModel.writer.value
        })
        movieViewModel.actor.observe(this, Observer {
            txt_actors.text = movieViewModel.actor.value
        })
        movieViewModel.plot.observe(this, Observer {
            txt_plot.text = movieViewModel.plot.value
        })
        movieViewModel.language.observe(this, Observer {
            tct_language.text = movieViewModel.language.value
        })
        movieViewModel.country.observe(this, Observer {
            txt_country.text = movieViewModel.country.value
        })
        movieViewModel.awards.observe(this, Observer {
            txt_awards.text = movieViewModel.awards.value
        })
        movieViewModel.production.observe(this, Observer {
            txt_production.text = movieViewModel.production.value
        })
        movieViewModel.released.observe(this, Observer {
            txt_released.text = movieViewModel.released.value
        })


    }
}