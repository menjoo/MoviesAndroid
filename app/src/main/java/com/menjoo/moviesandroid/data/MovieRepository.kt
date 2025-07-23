package com.menjoo.moviesandroid.data

import com.menjoo.moviesandroid.data.model.SearchResult


class MovieRepository(private val movieDbApi: TheMovieDbApi) {

    suspend fun getMoviesNowInCinema(page: Int = 1): SearchResult {
        return movieDbApi.getMoviesInCinema(page)
    }
}
