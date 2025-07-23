package com.menjoo.moviesandroid.cinema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.menjoo.moviesandroid.data.Injection
import com.menjoo.moviesandroid.data.model.Movie
import com.menjoo.moviesandroid.util.MoviePager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CinemaViewModel : ViewModel() {

    private val movieRepository = Injection.movieRepository

    private var moviePager: MoviePager = MoviePager(null)

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow(false)
    val error: StateFlow<Boolean> = _error

    init {
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        viewModelScope.launch {
            try {
                _error.value = false
                _loading.value = true
                val searchResult = withContext(Dispatchers.IO) {
                    movieRepository.getMoviesNowInCinema(page)
                }
                moviePager = MoviePager(searchResult)
                val updated = if (page == 1) {
                    searchResult.results
                } else {
                    _movies.value + searchResult.results
                }
                _movies.value = updated
            } catch (_: Exception) {
                _error.value = true
            } finally {
                _loading.value = false
            }
        }
    }

    fun onLoadMore() {
        if (moviePager.hasNextPage()) {
            loadMovies(moviePager.nextPage())
        }
    }

    fun onRefreshPulled() {
        _movies.value = emptyList()
        loadMovies()
    }


}
