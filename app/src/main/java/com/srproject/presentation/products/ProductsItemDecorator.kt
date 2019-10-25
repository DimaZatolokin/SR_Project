package com.srproject.presentation.products

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProductsItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position == state.itemCount - 1) {
            outRect.bottom = (78 * view.context.resources.displayMetrics.density).toInt()
        }
    }

}