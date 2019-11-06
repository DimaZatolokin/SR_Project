package com.srproject.presentation.activeOdrersList

import androidx.recyclerview.widget.DiffUtil
import com.srproject.presentation.models.OrderUI

class OrdersDiffUtils(private var oldItems: List<OrderUI>, private var newItems: List<OrderUI>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}