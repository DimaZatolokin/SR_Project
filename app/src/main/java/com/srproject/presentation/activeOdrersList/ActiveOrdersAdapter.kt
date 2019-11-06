package com.srproject.presentation.activeOdrersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemActiveOrderBinding
import com.srproject.presentation.models.OrderUI
import kotlin.properties.Delegates

class ActiveOrdersAdapter(private val orderClickListener: ActiveOrderClickListener) :
    RecyclerView.Adapter<ActiveOrdersAdapter.VH>() {

    var items: List<OrderUI> by Delegates.observable(emptyList()) { _, oldList, newList ->
        notifyChanges(oldList, newList)
    }

    private fun notifyChanges(oldList: List<OrderUI>, newList: List<OrderUI>) {
        val diff = DiffUtil.calculateDiff(OrdersDiffUtils(oldList, newList))
        diff.dispatchUpdatesTo(this)
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

    inner class VH(private val binding: ItemActiveOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(order: OrderUI) {
            binding.model = order
            binding.rvPositions.adapter = OrdersPositionsAdapter(order.positions)
            binding.root.setOnClickListener {
                orderClickListener.onOrderClicked(order.id!!)
            }
        }
    }
}