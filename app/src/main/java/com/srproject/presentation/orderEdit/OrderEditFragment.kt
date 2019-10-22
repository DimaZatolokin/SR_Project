package com.srproject.presentation.orderEdit

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.srproject.R
import com.srproject.common.OnBackPressedListener
import com.srproject.common.obtainViewModel
import com.srproject.common.toTimeStamp
import com.srproject.databinding.FragmentOrderEditBinding
import com.srproject.presentation.BaseFragment
import java.util.*

class OrderEditFragment : BaseFragment<FragmentOrderEditBinding>(), OrderEditActionListener,
    OnBackPressedListener {

    private val viewModel: OrderEditViewModel by lazy {
        obtainViewModel(OrderEditViewModel::class.java)
    }
    private val args: OrderEditFragmentArgs by navArgs()

    override val contentLayoutId = R.layout.fragment_order_edit

    override fun setupBinding(binding: FragmentOrderEditBinding) {
        binding.viewModel = viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.apply {
            start(args.id)
            navigateBackCommand.observe(this@OrderEditFragment, Observer {
                findNavController().popBackStack()
            })
            showExitDialogCommand.observe(this@OrderEditFragment, androidx.lifecycle.Observer {
                showQuestionDialog(message = getString(R.string.save_changes),
                    actionAccept = {
                        viewModel.onSaveClicked()
                    }, actionDecline = {
                        findNavController().popBackStack()
                    })
            })
            showErrorCommand.observe(this@OrderEditFragment, Observer {
                val message = when(it) {
                    Errors.CONSUMER -> getString(R.string.error_empty_consumer)
                    Errors.POSITIONS -> getString(R.string.error_empty_positions)
                }
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            })
        }
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
            val currentDueDate = viewModel.dueDate.get()!!.toTimeStamp()
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

    override fun onBackPressed(): Boolean {
        viewModel.backPressed()
        return false
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
