package com.srproject.presentation.consumers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemConsumerBinding

class ConsumersAdapter(private val listener: (String) -> Unit) :
    RecyclerView.Adapter<ConsumersAdapter.VH>() {

    var items = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemConsumerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_consumer,
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemConsumerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvConsumer.text = item
            binding.root.setOnClickListener {
                listener.invoke(item)
            }
        }
    }
}