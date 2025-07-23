package com.menjoo.moviesandroid.cinema

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.menjoo.moviesandroid.R
import com.menjoo.moviesandroid.util.EndlessRecyclerViewScrollListener
import com.menjoo.moviesandroid.util.extensions.asVisibility
import kotlinx.android.synthetic.main.cinema_fragment.*


class CinemaFragment : Fragment() {

    companion object {
        private val NUMBER_OF_COLUMNS: Int = 2
    }

    private lateinit var viewModel: CinemaViewModel
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cinema_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CinemaViewModel::class.java)
        setupRecyclerView()
        setupPullToRefresh()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = MovieAdapter(ArrayList())
        recyclerView.adapter = adapter

        val scrollListener: EndlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                viewModel.onLoadMore()
            }

        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun setupPullToRefresh() {
        pullToRefreshIndicator.setOnRefreshListener { viewModel.onRefreshPulled() }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.movies.collect { adapter.setItems(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loading.collect { pullToRefreshIndicator.isRefreshing = it }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect { errorMessage.visibility = it.asVisibility() }
        }
    }
}
