package com.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.network.Loading
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import retrofit2.await


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        recycleView.visibility = View.GONE
        button.onClick {
            var title = editText2.text.toString()
            recycleView.visibility = View.VISIBLE

            Loading.showLoading(this@MainActivity)
                launch {
                    try {
                        val data = movieViewModel.getMovieListFromAPI(title).await()
                        Log.d("Response--->",data.toString())
                        if(data!=null && data.Response=="True"){

                            movieViewModel.getTnCList(data.Search!!).observe(this@MainActivity,
                                Observer {
                                    val layoutM = LinearLayoutManager(this@MainActivity)
                                    layoutM.orientation = LinearLayoutManager.VERTICAL
                                    recycleView.apply {
                                        layoutManager = layoutM
                                        movieListAdapter = MovieListAdapter(context,it){ itemClicked, _ ->
                                            val id = itemClicked.getTnC.imdbID
                                            startActivity<MovieDetailActivity>("id" to id)
                                        }
                                    }
                                    recycleView.adapter = movieListAdapter
                                    movieListAdapter.notifyDataSetChanged()
                                })
                            Loading.hideLoading()
                        }

                    } catch(e: Exception) {
                        e.printStackTrace()
                        Log.d("Response--->","failed")
                        Loading.hideLoading()

                    }

                }
            }

    }



}