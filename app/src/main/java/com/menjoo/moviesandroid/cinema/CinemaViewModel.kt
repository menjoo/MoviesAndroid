package com.menjoo.moviesandroid.cinema

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.menjoo.moviesandroid.data.Injection
import com.menjoo.moviesandroid.data.model.Movie

class CinemaViewModel : ViewModel(), CinemaContract.View {

    override var presenter: CinemaContract.Presenter = CinemaPresenter(Injection.movieRepository, this)

    val observableMovies: LiveData<List<Movie>> = MutableLiveData()
    private val movies: ArrayList<Movie> = ArrayList()
    var loading: LiveData<Boolean> = MutableLiveData()
    var error: LiveData<Boolean> = MutableLiveData()

    init {
        presenter.start()
    }

    override fun addMoviesToList(moviesToShow: List<Movie>) {
        movies.addAll(moviesToShow)
        (observableMovies as MutableLiveData).value = movies
    }

    override fun clearMovies() {
        movies.clear()
        (observableMovies as MutableLiveData).value = movies
    }

    override fun showLoading() {
        (loading as MutableLiveData).value = true
    }

    override fun hideLoading() {
        (loading as MutableLiveData).value = false
    }

    override fun showError() {
        (error as MutableLiveData).value = true
    }

    override fun hideError() {
        (error as MutableLiveData).value = false
    }

    override fun onCleared() {
        presenter.stop()
    }
}
