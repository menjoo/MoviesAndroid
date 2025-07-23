package com.menjoo.moviesandroid.cinema

import com.menjoo.moviesandroid.data.model.Movie
import com.menjoo.moviesandroid.util.mvp.BasePresenter
import com.menjoo.moviesandroid.util.mvp.BaseView

interface CinemaContract {

    interface View : BaseView<Presenter> {
        fun addMoviesToList(moviesToShow: List<Movie>)
        fun clearMovies()
        fun showLoading()
        fun hideLoading()
        fun showError()
        fun hideError()
    }

    interface Presenter : BasePresenter {
        fun onRefreshPulled()
        fun onLoadMore()
    }
}
