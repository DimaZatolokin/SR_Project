package com.srproject.presentation.createOrder

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.OnBackPressedListener
import com.srproject.common.obtainViewModel
import com.srproject.common.toTimeStamp
import com.srproject.databinding.FragmentOrderCreateBinding
import com.srproject.presentation.BaseFragment
import java.util.*

class OrderCreateFragment : BaseFragment<FragmentOrderCreateBinding>(), OnBackPressedListener,
    OrderCreateActionListener {

    private val viewModel: OrderCreateViewModel by lazy {
        obtainViewModel(OrderCreateViewModel::class.java)
    }
    override val contentLayoutId = R.layout.fragment_order_create

    override fun setupBinding(binding: FragmentOrderCreateBinding) {
        binding.viewModel = viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.start()
        viewModel.navigateBackCommand.observe(this, androidx.lifecycle.Observer {
            findNavController().popBackStack()
        })
    }

    override fun onBackPressed(): Boolean {

        return true
    }

    override fun onSaveClick() {
        viewModel.onSaveClicked()
    }

    override fun onDateCreatedClick() {
        activity?.run {
            val currentDateCreated = viewModel.dateCreated.get()!!.toTimeStamp()
            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentDateCreated
            }
            val dialog = DatePickerDialog(
                this,
                DateCreatedSetListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.datePicker.maxDate = System.currentTimeMillis()
            dialog.show()
        }
    }

    override fun onDueDateClick() {
        activity?.run {
            val inputDate = viewModel.dueDate.get()
            val currentDueDate = inputDate?.toTimeStamp() ?: System.currentTimeMillis()
            val calendar = Calendar.getInstance().apply {
                timeInMillis = currentDueDate
            }
            val dialog = DatePickerDialog(
                this,
                DueDateSetListener(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            val currentDateCreated = viewModel.dateCreated.get()!!.toTimeStamp()
            dialog.datePicker.minDate = currentDateCreated
            dialog.show()
        }
    }

    override fun onPaidClick() {
        viewModel.onPaidClicked()
    }

    override fun onDoneClick() {
        viewModel.onDoneClicked()
    }

    override fun onGivenClick() {
        viewModel.onGivenClicked()
    }

    override fun onAddPositionClick() {
        viewModel.onAddPositionClicked()
    }

    private inner class DateCreatedSetListener : DatePickerDialog.OnDateSetListener {

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            viewModel.setDateCreated(calendar.timeInMillis)
        }
    }

    private inner class DueDateSetListener : DatePickerDialog.OnDateSetListener {

        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            viewModel.setDueDate(calendar.timeInMillis)
        }
    }
}