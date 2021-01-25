package com.movies

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.network.Loading
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import org.jetbrains.anko.inputMethodManager
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
           // inputMethodManager.hideSoftInputFromWindow(Context.windowToken, 0)
            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            var title = editText2.text.toString()

            Loading.showLoading(this@MainActivity)
                launch {
                    try {
                        val data = movieViewModel.getMovieListFromAPI(title).await()
                        Log.d("Response--->",data.toString())
                        if(data!=null && data.Response=="True"){
                            recycleView.visibility = View.VISIBLE
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
                        }else{
                            Toast.makeText(this@MainActivity,"Movie Not found",Toast.LENGTH_LONG).show()
                            Loading.hideLoading()
                            recycleView.visibility = View.GONE
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