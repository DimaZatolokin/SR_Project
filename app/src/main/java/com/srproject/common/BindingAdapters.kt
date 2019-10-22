package com.srproject.common

import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.srproject.R

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
