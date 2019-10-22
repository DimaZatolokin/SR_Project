package com.srproject.presentation.customViews

import android.content.Context
import android.os.Handler
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.core.view.doOnLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.srproject.R
import com.srproject.databinding.ViewCustomAmountPickerBinding

const val MIN_VALUE = 0

class CustomAmountPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    private var binding: ViewCustomAmountPickerBinding =
        DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.view_custom_amount_picker,
            this,
            true
        )
    private var autoIncrement = false
    private var autoDecrement = false
    /** Used for long click handling: when user holds +/- buttons, values autoincrement */
    private val longClickHandler = Handler()

    /** Do not set this value in code directly. Use [value] */
    val textValue = ObservableField(MIN_VALUE.toString())
    val minValueReached = ObservableBoolean()

    var listener: CustomAmountPickerListener? = null

    var value: Int
        get() {
            val currentTextValue = textValue.get()

            return if (currentTextValue == null || currentTextValue.isEmpty() || currentTextValue.isBlank()) {
                0
            } else {
                currentTextValue.toInt()
            }
        }
        set(value) {
            val stringVal = value.toString()

            if (textValue.get() != stringVal) {
                textValue.set(stringVal)
                checkValueBoundaries(false)
            }
        }

    init {
        value = MIN_VALUE
        binding.layoutOwner = this
        checkValueBoundaries()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.minusImageButton.isEnabled = enabled
        binding.valueEditText.isEnabled = enabled
        binding.plusImageButton.isEnabled = enabled
    }

    fun onDecrementButtonClick() {
        decrement()
    }

    fun onDecrementButtonLongClick(): Boolean {
        autoDecrement = true
        longClickHandler.postDelayed(counterRunnable, LONG_CLICK_COUNTER_DELAY)
        return false
    }

    fun onDecrementButtonTouch(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && autoDecrement) {
            autoDecrement = false
        }
        return false
    }

    fun onIncrementButtonClick() {
        increment()
    }

    fun onIncrementButtonLongClick(): Boolean {
        autoIncrement = true
        longClickHandler.postDelayed(counterRunnable, LONG_CLICK_COUNTER_DELAY)
        return false
    }

    fun onIncrementButtonTouch(event: MotionEvent): Boolean {
        val currentAction = event.action
        val isActionCanceled =
            currentAction == MotionEvent.ACTION_UP || currentAction == MotionEvent.ACTION_MOVE
        if (isActionCanceled && autoIncrement) {
            autoIncrement = false
        }
        return false
    }

    fun onTextValueChanged() {
        val text = textValue.get() ?: return

        // Check that new text can be converted to Int
        if (text.toIntOrNull() == null) {
            value = MIN_VALUE
            return
        }

        if (text.isEmpty()) {
            value = MIN_VALUE
        } else if (text.length > 1 && text.first() == '0') {
            //user tries to input leading zero, so we need to remove it
            var trimmedText = text.trimStart('0')

            if (trimmedText.isEmpty()) trimmedText = MIN_VALUE.toString()

            value = trimmedText.toInt()
        }

        checkValueBoundaries()

        // Move cursor to the end if it remained at the beginning after text was entered. This can only happen when
        // we internally change value through increment/decrement
        binding.valueEditText.apply {
            if (selectionEnd == 0) setSelection(text.length)
        }

        listener?.onCustomAmountPickerValueChange(value, true)
    }

    fun onEditTextFocusChange(hasFocus: Boolean) {
        if (!hasFocus) {
            checkValueBoundaries(true)
        }
    }

    private fun decrement() {
        val currentVal = value
        value = if (currentVal > MIN_VALUE) {
            currentVal - 1
        } else {
            MIN_VALUE
        }
    }

    private fun increment() {
        val currentVal = value
        value = currentVal + 1
    }

    /**
     * Check that value is within \[minValue] boundaries
     * @param restrictMaxInput: When set to TRUE if user enters a value that is > maxValue from keyboard,
     *                          user input will be replaces with maxValue. In other case value will be
     *                          restricted after edit text loses it's focus
     */
    private fun checkValueBoundaries(restrictMaxInput: Boolean = false) {
        val valueToCheck = value
        if (valueToCheck < MIN_VALUE) value = MIN_VALUE
        when {
            valueToCheck == MIN_VALUE -> {
                setMinValueReached(true)
            }
            valueToCheck == MIN_VALUE -> {
                setMinValueReached(true)
            }
            else -> {
                setMinValueReached(false)
            }
        }
    }

    private fun setMinValueReached(isReached: Boolean) {
        if (minValueReached.get() != isReached) minValueReached.set(isReached)
        if (isReached) autoDecrement = false
    }

    companion object {
        private const val DEFAULT_MIN_VALUE = 0
        private const val LONG_CLICK_COUNTER_DELAY = 50L //millis
    }

    /** Used for long click handling: when user holds +/- buttons, values autoincrement */
    private val counterRunnable = object : Runnable {
        override fun run() {
            if (autoIncrement) {
                increment()
                handler.postDelayed(this, LONG_CLICK_COUNTER_DELAY)
            } else if (autoDecrement) {
                decrement()
                handler.postDelayed(this, LONG_CLICK_COUNTER_DELAY)
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Adjust editText text size
        with(binding.valueEditText) {
            val density = resources.displayMetrics.densityDpi
            doOnLayout {
                var textWidth: Float
                val inputWidth = this.width - this.paddingStart - this.paddingEnd
                var iterations =
                    0 //just for some unexpected cases to make sure that we're not in an infinite or long-running loop
                do {
                    paint.textSize = paint.textSize - 2 / density
                    textWidth = paint.measureText("1234")
                    iterations++
                } while (textWidth > inputWidth && iterations < 20)
                this.invalidate()
            }
        }
    }

    //region ::STATE SAVING
    internal class SavedState : BaseSavedState {
        var amount: Int = 0

        constructor(source: Parcel) : super(source) {
            amount = source.readInt()
        }

        constructor(superState: Parcelable) : super(superState)

        override fun writeToParcel(out: Parcel?, flags: Int) {
            super.writeToParcel(out, flags)
            out?.writeInt(amount)
        }

        companion object {
            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(source: Parcel): SavedState {
                    return SavedState(source)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState() ?: return super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.amount = value

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            value = state.amount
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    //endregion

    interface CustomAmountPickerListener {
        fun onCustomAmountPickerValueChange(newValue: Int, isValid: Boolean = true)
    }
}
