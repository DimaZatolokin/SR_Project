package com.srproject.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.data.models.Product
import com.srproject.databinding.ItemProductLayoutBinding

class ProductsAdapter(private val listener: ProductEditClickListener) :
    RecyclerView.Adapter<ProductsAdapter.VH>() {

    var items = listOf<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding: ItemProductLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product_layout,
            parent,
            false
        )
        return VH(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    inner class VH(private val binding: ItemProductLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            binding.model = item
            binding.btnEdit.setOnClickListener { listener.onEditClicked(item.id!!) }
        }
    }
}