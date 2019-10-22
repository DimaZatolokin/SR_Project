package com.srproject.presentation.createOrder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.srproject.R
import com.srproject.data.models.Product
import com.srproject.databinding.ItemOrderPositionCreateBinding
import com.srproject.presentation.customViews.CustomAmountPicker
import com.srproject.presentation.models.OrderPositionUI

class OrderCreatePositionsAdapter(private val updatePriceListener: UpdatePriceListener) :
    RecyclerView.Adapter<OrderCreatePositionsAdapter.VH>() {

    var products: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    val items: ArrayList<OrderPositionUI> = arrayListOf()

    fun getTotalPrice(): Int {
        return items.sumBy { it.amount * it.product.price }
    }

    fun createNewItem() {
        if (products.isNotEmpty()) {
            items.add(OrderPositionUI(-1, products.first(), 0, -1))
            notifyDataSetChanged()
        }
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

        init {
            binding.spProducts.adapter = ArrayAdapter<String>(
                binding.root.context,
                android.R.layout.simple_spinner_item,
                products.map { it.name }
            ).apply {
                setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            }
            binding.spProducts.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        binding.model?.product = products[position]
                        updatePriceListener.onUpdatePrice()
                    }

                }
            binding.amountPicker.listener = object : CustomAmountPicker.CustomAmountPickerListener {

                override fun onCustomAmountPickerValueChange(newValue: Int, isValid: Boolean) {
                    binding.model?.amount = newValue
                    updatePriceListener.onUpdatePrice()
                }

            }
        }

        fun bind(item: OrderPositionUI) {
            binding.model = item
            binding.amountPicker.value = item.amount
        }
    }
}