package com.india.rentzgo

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import single.NearbyProperties

class InfiniteScrollListener(manager: LinearLayoutManager, onLoadMoreListener: OnLoadMoreListener) :
    RecyclerView.OnScrollListener() {
    private val VISIBLE_THRESHOLD: Int = 2
    private val manager = manager
    private val listener: OnLoadMoreListener = onLoadMoreListener
    private var isScrolling: Boolean = false

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dx == 0 && dy == 0)
            return

        val totalItemCount = manager.itemCount
        val lastVisibleItem = manager.findLastVisibleItemPosition()
        val currentItems = manager.childCount

        var totalSize = NearbyProperties.list.size
        var loadedItems =  NearbyProperties.index

        if (isScrolling && totalSize > loadedItems) {
            if (listener != null)
                listener.onLoadMore()
            isScrolling = false
        }
    }
}