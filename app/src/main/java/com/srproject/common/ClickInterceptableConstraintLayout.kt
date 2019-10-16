package com.srproject.common

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class ClickInterceptableConstraintLayout(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    override fun onInterceptTouchEvent(ev: MotionEvent?) = true
}
