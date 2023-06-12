package com.widget.arc.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ArcItemDecoration (var space: Int) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
    }
}