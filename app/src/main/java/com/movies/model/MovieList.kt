package com.movies.model

object MovieList {

    data class Response(
        val Response: String?,
        val totalResults: String?,
        val Search: MutableList<Data>?

    ) {
        data class Data(
            val imdbID: String?,
            val Poster: String?,
            val Title: String?,
            val Type: String?,
            val Year: String?

            )
    }
}