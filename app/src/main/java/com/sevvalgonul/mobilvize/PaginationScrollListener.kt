package com.sevvalgonul.mobilvize

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



abstract class PaginationScrollListener(private val layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        println("onScrolled dx=" + dx + " dy=" + dy)
        if (!isLoading() && !isLastPage()) {
            val visibleCount = visibleItemCount + firstVisibleItemPosition
            println("visibleCount=" + visibleCount + " totalItemCount=" + totalItemCount +
                    " firstVisibleItemPosition=" + firstVisibleItemPosition )
            if ((visibleCount >= totalItemCount)
                && (firstVisibleItemPosition >= 0)) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}