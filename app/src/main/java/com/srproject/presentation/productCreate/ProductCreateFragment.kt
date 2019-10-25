package com.srproject.presentation.productCreate

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentProductCreateBinding
import com.srproject.presentation.BaseFragment

class ProductCreateFragment : BaseFragment<FragmentProductCreateBinding>(),
    ProductCreateActionListener {

    private val viewModel: ProductCreateViewModel by lazy {
        obtainViewModel(ProductCreateViewModel::class.java)
    }
    override val contentLayoutId = R.layout.fragment_product_create

    override fun setupBinding(binding: FragmentProductCreateBinding) {
        binding.listener = this
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.apply {
            navigateBackCommand.observe(this@ProductCreateFragment, Observer {
                findNavController().popBackStack()
            })
            showErrorCommand.observe(this@ProductCreateFragment, Observer {
                val stringId = when (it) {
                    Errors.NAME -> R.string.error_empty_product_name
                    Errors.PRICE -> R.string.error_empty_product_price
                }
                Toast.makeText(activity, stringId, Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onCreateClick() {
        viewModel.createProduct()
    }
}