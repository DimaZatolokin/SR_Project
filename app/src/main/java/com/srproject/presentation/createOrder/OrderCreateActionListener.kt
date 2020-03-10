package com.srproject.presentation.createOrder

interface OrderCreateActionListener {

    fun onSaveClick()
    fun onDateCreatedClick()
    fun onDueDateClick()
    fun onPaidClick()
    fun onDoneClick()
    fun onGivenClick()
    fun onAddPositionClick()
    fun onCustomerFocusChange(hasFocus: Boolean)
}