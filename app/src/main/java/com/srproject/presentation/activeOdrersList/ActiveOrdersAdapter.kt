package com.srproject.presentation.activeOdrersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemActiveOrderBinding
import com.srproject.presentation.models.OrderUI

class ActiveOrdersAdapter(private val orderClickListener: ActiveOrderClickListener) :
    RecyclerView.Adapter<ActiveOrdersAdapter.VH>() {

    var items = emptyList<OrderUI>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemActiveOrderBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_active_order,
                parent,
                false
            )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemActiveOrderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderUI) {
            binding.model = order
            binding.rvPositions.adapter = OrdersPositionsAdapter(order.positions)
            binding.root.setOnClickListener {
                orderClickListener.onOrderClicked(order.id!!)
            }
        }
    }
}