package com.srproject.presentation.products

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentProductsBinding
import com.srproject.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment<FragmentProductsBinding>(), ProductsActionListener {

    private val viewModel: ProductsViewModel by lazy {
        obtainViewModel(ProductsViewModel::class.java)
    }
    override val contentLayoutId = R.layout.fragment_products

    override fun setupBinding(binding: FragmentProductsBinding) {
        binding.listener = this
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
            navigateToProductEditCommand.observe(this@ProductsFragment, Observer {
                findNavController().navigate(ProductsFragmentDirections.actionToProductEdit(it))
            })
        }
    }

    override fun setupViews() {
        rvProducts.addItemDecoration(ProductsItemDecorator())
    }

    override fun onAddProductClick() {
        findNavController().navigate(ProductsFragmentDirections.actionToProductCreate())
    }
}