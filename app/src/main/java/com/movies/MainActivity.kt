package com.movies

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.movies.model.MovieList
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
    var listTnCLiveData = MutableLiveData<ArrayList<MovieViewModel>>()
    var arrayListTnC = ArrayList<MovieViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        recycleView.visibility = View.GONE
        button.onClick {
            var title = editText2.text.toString()
            recycleView.visibility = View.VISIBLE
            /*button.visibility = View.GONE
            editText2.visibility = View.GONE
            textView2.text = "Movie List: " + title*/

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

                           /* (viewLifecycleOwner, Observer {
                                adapterTermCondition = TermConditionPackageAdapter(it)
                                binding.rvTnc.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                binding.rvTnc.adapter = adapterTermCondition
                                adapterTermCondition.notifyDataSetChanged()
                            })*/

                          /*
                            var serarch = data.Search

                            arrayListTnC.clear()
                            if (serarch != null) {
                                for (i in 0 until serarch.size) {
                                    arrayListTnC.add(MovieViewModel(
                                        MovieList.Response.Data(
                                        serarch[i].imdbID,
                                        serarch[i].Poster,
                                        serarch[i].Title,
                                        serarch[i].Type,
                                        serarch[i].Year)))

                                }
                            }
                            listTnCLiveData.value = arrayListTnC
                            Loading.hideLoading()

                           */
                            Loading.hideLoading()
                        }

                    } catch(e: Exception) {
                        e.printStackTrace()
                        Log.d("Response--->","failed")
                        Loading.hideLoading()
                        button.visibility = View.VISIBLE
                        editText2.visibility = View.VISIBLE
                        textView2.text = "Search By Movie Title"
                    }

                }
            }

    }



}