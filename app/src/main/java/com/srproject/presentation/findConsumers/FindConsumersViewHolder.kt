package com.srproject.presentation.findConsumers

import androidx.recyclerview.widget.RecyclerView
import com.srproject.databinding.ItemFindConsumersBinding

class FindConsumersViewHolder(
    private val binding: ItemFindConsumersBinding,
    private val clickListener: OnConsumerClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            clickListener.onItemClicked(binding.tvConsumer.text.toString())
        }
    }


    fun bind(consumer: String) {
        binding.tvConsumer.text = consumer
    }
}