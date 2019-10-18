package com.srproject.presentation.createOrder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemOrderPositionCreateBinding
import com.srproject.presentation.models.OrderPositionUI

class OrderCreatePositionsAdapter : RecyclerView.Adapter<OrderCreatePositionsAdapter.VH>() {

    var products: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var items: List<OrderPositionUI> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addItem(item: OrderPositionUI) {
        val newList = arrayListOf<OrderPositionUI>()
        newList.addAll(items)
        newList.add(item)
        items = newList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemOrderPositionCreateBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_order_position_create,
                parent,
                false
            )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemOrderPositionCreateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderPositionUI: OrderPositionUI) {
            binding.model = orderPositionUI
            binding.spProducts.adapter = ArrayAdapter<String>(binding.root.context, android.R.layout.simple_spinner_item, products).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            }
        }
    }
}