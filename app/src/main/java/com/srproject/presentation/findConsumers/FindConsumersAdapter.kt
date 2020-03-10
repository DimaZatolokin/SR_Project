package com.srproject.presentation.findConsumers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.databinding.ItemFindConsumersBinding

class FindConsumersAdapter(private val listener: OnConsumerClickListener) : RecyclerView.Adapter<FindConsumersViewHolder>() {

    var items: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindConsumersViewHolder {
        val binding: ItemFindConsumersBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_find_consumers,
                parent,
                false
            )
        return FindConsumersViewHolder(binding, listener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FindConsumersViewHolder, position: Int) {
        holder.bind(items[position])
    }

}