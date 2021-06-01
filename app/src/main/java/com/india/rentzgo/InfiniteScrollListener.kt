package com.india.rentzgo

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteScrollListener(manager: LinearLayoutManager, onLoadMoreListener: OnLoadMoreListener) :
    RecyclerView.OnScrollListener() {
    private val VISIBLE_THRESHOLD: Int = 2
    private val manager = manager
    private val listener: OnLoadMoreListener = onLoadMoreListener
    private var isLoading: Boolean = false

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dx == 0 && dy == 0)
            return

        val totalItemCount = manager.itemCount
        val lastVisibleItem = manager.findLastVisibleItemPosition()
        val currentItems = manager.childCount

        if (!isLoading && totalItemCount <= lastVisibleItem + currentItems - 1) {
            if (listener != null) {
                listener.onLoadMore()
            }
            isLoading = true
        }
    }

    fun setLoaded() {
        isLoading = false
    }
}