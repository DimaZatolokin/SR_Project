package com.srproject.presentation.createOrder

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
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
        viewModel.apply {
            start()
            navigateBackCommand.observe(this@OrderCreateFragment, androidx.lifecycle.Observer {
                findNavController().popBackStack()
            })
            showExitDialogCommand.observe(this@OrderCreateFragment, androidx.lifecycle.Observer {
                showQuestionDialog(message = getString(R.string.save_changes),
                    actionAccept = {
                        viewModel.onSaveClicked()
                    }, actionDecline = {
                        findNavController().popBackStack()
                    })
            })
            showErrorCommand.observe(this@OrderCreateFragment, androidx.lifecycle.Observer {
                val message = when (it) {
                    Errors.CONSUMER -> getString(R.string.error_empty_consumer)
                    Errors.DUE_DATE -> getString(R.string.error_empty_due_date)
                    Errors.POSITIONS -> getString(R.string.error_empty_positions)
                }
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onBackPressed(): Boolean {
        viewModel.backPressed()
        return false
    }

    override fun onSaveClick() {
        viewModel.onSaveClicked()
    }

    override fun onDateCreatedClick() {
        hideKeyboard()
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
        hideKeyboard()
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
        hideKeyboard()
        viewModel.onPaidClicked()
    }

    override fun onDoneClick() {
        hideKeyboard()
        viewModel.onDoneClicked()
    }

    override fun onGivenClick() {
        hideKeyboard()
        viewModel.onGivenClicked()
    }

    override fun onAddPositionClick() {
        hideKeyboard()
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