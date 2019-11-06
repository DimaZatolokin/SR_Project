package com.srproject.common

import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.srproject.R
import com.srproject.presentation.models.OrderUI

@BindingAdapter("done")
fun TextView.setOrderDone(done: Boolean) {
    if (done) {
        text = context.getString(R.string.order_done)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_checkmark),
            null,
            null,
            null
        )
    } else {
        text = context.getString(R.string.order_in_progress)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_inprogress),
            null,
            null,
            null
        )
    }
}

@BindingAdapter("positionDone")
fun ImageView.setOrderPositionDone(done: Boolean) {
    setImageDrawable(context.getDrawable(if (done) R.drawable.ic_done_green else R.drawable.ic_inprogress))
}

@BindingAdapter("status")
fun TextView.setStatus(active: Boolean) {
    if (active) {
        text = context.getString(R.string.no)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_red_cross),
            null,
            null,
            null
        )
    } else {
        text = context.getString(R.string.yes)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_checkmark),
            null,
            null,
            null
        )
    }
}

@BindingAdapter("paid")
fun TextView.setPaid(paid: Boolean) {
    if (paid) {
        text = context.getString(R.string.yes)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_checkmark),
            null,
            null,
            null
        )
    } else {
        text = context.getString(R.string.no)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_red_cross),
            null,
            null,
            null
        )
    }
}

@BindingAdapter("active")
fun TextView.setActive(active: Boolean) {
    if (active) {
        text = context.getString(R.string.not_given)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_red_cross),
            null,
            null,
            null
        )
    } else {
        text = context.getString(R.string.given)
        setCompoundDrawablesWithIntrinsicBounds(
            context.getDrawable(R.drawable.ic_checkmark),
            null,
            null,
            null
        )
    }
}

@BindingAdapter("price")
fun TextView.setPrice(order: OrderUI) {
    val price = if (order.price > 0) order.price else order.calculatedPrice
    text = String.format(context.getString(R.string.price), price)
}

@BindingAdapter("selectedPositionItem")
fun Spinner.setSelectedPositionItem(item: String) {
    val position = (adapter as ArrayAdapter<String>).getPosition(item)
    this.setSelection(position)
}

@BindingAdapter("onTouch")
fun View.setTouchListener(onTouchListener: View.OnTouchListener) {
    setOnTouchListener(onTouchListener)
}

@BindingAdapter("onFocusChange")
fun View.onFocusChange(listener: View.OnFocusChangeListener) {
    onFocusChangeListener = listener
}

@BindingAdapter("filterSelected")
fun TextView.setFilterSelected(selected: Boolean) {
    background = if (selected) {
        context.getDrawable(R.drawable.filter_item_selected_bg)
    } else {
        context.getDrawable(R.drawable.filter_item_unselect_bg)
    }
}

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}

