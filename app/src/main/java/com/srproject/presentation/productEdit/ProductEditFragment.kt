package com.srproject.presentation.productEdit

import android.os.Handler
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentProductEditBinding
import com.srproject.presentation.BaseFragment

class ProductEditFragment : BaseFragment<FragmentProductEditBinding>(), ProductEditActionListener {

    private val viewModel: ProductEditViewModel by lazy {
        obtainViewModel(ProductEditViewModel::class.java)
    }
    private val args: ProductEditFragmentArgs by navArgs()
    override val contentLayoutId = R.layout.fragment_product_edit

    override fun setupBinding(binding: FragmentProductEditBinding) {
        binding.listener = this
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.apply {
            start(args.id)
            navigateBackCommand.observe(this@ProductEditFragment, Observer {
                findNavController().popBackStack()
            })
            showErrorCommand.observe(this@ProductEditFragment, Observer {
                val stringId = when (it) {
                    Errors.NAME -> R.string.error_empty_product_name
                    Errors.PRICE -> R.string.error_empty_product_price
                    Errors.PRODUCT_USED -> R.string.error_product_used
                    Errors.PRODUCT_NOT_FOUND -> {
                        Handler().postDelayed({
                            findNavController().popBackStack()
                        }, 1000)
                        R.string.error_product_not_found
                    }
                }
                Toast.makeText(activity, stringId, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onSaveClick() {
        viewModel.onSaveClicked()
    }

    override fun onDeleteClick() {
        showQuestionDialog(message = getString(R.string.delete_product_message), actionAccept = {
            viewModel.onDeleteClicked()
        }, actionDecline = {})
    }
}