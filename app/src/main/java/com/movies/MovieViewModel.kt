package com.movies

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import com.movies.model.MovieDetails
import com.movies.model.MovieList
import com.movies.network.ApiEndPoint
import com.movies.network.Loading
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class MovieViewModel: ViewModel  {

        constructor() : super()

        companion object {
                var BaseUrl = "http://www.omdbapi.com"
            var ApiKey = "b9bd48a6"
        }

    private var _title = MutableLiveData<String>()
    var title: MutableLiveData<String>
        get() = _title
        set(value) {
            _title = value
        }

    private var _poster = MutableLiveData<String>()
    var poster: MutableLiveData<String>
        get() = _poster
        set(value) {
            _poster = value
        }

    private var _year = MutableLiveData<String>()
    var year: MutableLiveData<String>
        get() = _year
        set(value) {
            _year = value
        }

    private var _director = MutableLiveData<String>()
    var director: MutableLiveData<String>
        get() = _director
        set(value) {
            _director = value
        }
    private var _released = MutableLiveData<String>()
    var released: MutableLiveData<String>
        get() = _released
        set(value) {
            _released = value
        }

    private var _writer = MutableLiveData<String>()
    var writer: MutableLiveData<String>
        get() = _writer
        set(value) {
            _writer = value
        }

    private var _actor = MutableLiveData<String>()
    var actor: MutableLiveData<String>
        get() = _actor
        set(value) {
            _actor = value
        }

    private var _plot = MutableLiveData<String>()
    var plot: MutableLiveData<String>
        get() = _plot
        set(value) {
            _plot = value
        }

    private var _language = MutableLiveData<String>()
    var language: MutableLiveData<String>
        get() = _language
        set(value) {
            _language = value
        }
    private var _country = MutableLiveData<String>()
    var country: MutableLiveData<String>
        get() = _country
        set(value) {
            _country = value
        }
    private var _awards = MutableLiveData<String>()
    var awards: MutableLiveData<String>
        get() = _awards
        set(value) {
            _awards = value
        }
    private var _production = MutableLiveData<String>()
    var production: MutableLiveData<String>
        get() = _production
        set(value) {
            _production = value
        }



        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(ApiEndPoint::class.java)


        lateinit var getTnC: MovieList.Response.Data

        constructor(getTnC: MovieList.Response.Data) : super() {
                this.getTnC = getTnC
        }

        var listTnCLiveData = MutableLiveData<ArrayList<MovieViewModel>>()
        var arrayListTnC = ArrayList<MovieViewModel>()

    fun getTnCList(data: MutableList<MovieList.Response.Data>): MutableLiveData<ArrayList<MovieViewModel>> {
        arrayListTnC.clear()
        for (i in 0 until data.size) {
            arrayListTnC.add(MovieViewModel(MovieList.Response.Data(
                data[i].imdbID,
                data[i].Poster,
                data[i].Title,
                data[i].Type,
                data[i].Year)))
        }
        listTnCLiveData.value = arrayListTnC
        return listTnCLiveData
    }



    fun getMovieListFromAPI(title: String): Call<MovieList.Response> {
                return service.getMovieList(ApiKey,title,"movie")
        }

    fun getMovieDetailsFromAPI(i:String) : Call<MovieDetails.Response>{
        return service.getMovieDetails(ApiKey,i)
    }



    fun makeMovieDetailRequest(id : String) {
        viewModelScope.launch {
            // val jsonBody = "{ username: \"$username\", token: \"$token\"}"
            try {
                val data = getMovieDetailsFromAPI(id).await()
                Log.d("Response--->",data.toString())
                if(data!=null){
                    if(data.Response== "True"){
                         title.value = data.Title
                         poster.value = data.Poster
                         year.value = data.Year
                         director.value = data.Director
                        writer.value =data.Writer
                        actor.value =data.Actors
                        plot.value =data.Plot
                        language.value =data.Language
                        country.value =data.Country
                        awards.value =data.Awards
                        production.value =data.Production
                        released.value = data.Released

                    }

                }

            } catch(e: Exception) {
                e.printStackTrace()
                Log.d("Response--->","failed")

            }

        }
    }



}