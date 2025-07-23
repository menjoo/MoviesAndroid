package com.menjoo.moviesandroid.cinema

import com.menjoo.moviesandroid.data.MovieRepository
import com.menjoo.moviesandroid.util.MoviePager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class CinemaPresenter(private val movieRepository: MovieRepository,
                      private val view: CinemaContract.View) : CinemaContract.Presenter {
    private val disposables = CompositeDisposable()

    private var moviePager: MoviePager = MoviePager(null)

    init {
        view.presenter = this
    }

    override fun start() {
        view.hideError()
        view.showLoading()
        loadMovies()
    }

    private fun loadMovies(page: Int = 1) {
        disposables.add(movieRepository.getMoviesNowInCinema(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { searchResult ->
                            this.moviePager = MoviePager(searchResult)
                            view.addMoviesToList(searchResult.results)
                        },
                        onError = {
                            view.hideLoading()
                            view.showError()
                        },
                        onComplete = {
                            view.hideLoading()
                        }
                )
        )
    }

    override fun onLoadMore() {
        if (moviePager.hasNextPage()) {
            loadMovies(moviePager.nextPage())
        }
    }

    override fun onRefreshPulled() {
        view.hideError()
        view.clearMovies()
        view.showLoading()
        loadMovies()
    }

    override fun stop() {
        disposables.dispose()
    }
}
