package com.srproject.presentation.odrerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemOrderPositionMainBinding
import com.srproject.presentation.models.OrderPositionUI

class OrdersPositionsAdapter(private val items: List<OrderPositionUI>) : RecyclerView.Adapter<OrdersPositionsAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemOrderPositionMainBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_position_main,
                parent,
                false
            )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemOrderPositionMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderPositionUI: OrderPositionUI) {
            binding.model = orderPositionUI
        }
    }
}