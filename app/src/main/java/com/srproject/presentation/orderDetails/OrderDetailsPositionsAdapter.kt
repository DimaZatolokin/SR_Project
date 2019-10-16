package com.srproject.presentation.orderDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemOrderPositionDetailsBinding
import com.srproject.presentation.models.OrderPositionUI

class OrderDetailsPositionsAdapter : RecyclerView.Adapter<OrderDetailsPositionsAdapter.VH>() {

    var items: List<OrderPositionUI> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemOrderPositionDetailsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_position_details,
                parent,
                false
            )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemOrderPositionDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderPositionUI: OrderPositionUI) {
            binding.model = orderPositionUI
        }
    }
}