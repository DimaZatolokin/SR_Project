package com.srproject.presentation.orderDetails

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentOrderDetailsBinding
import com.srproject.presentation.BaseFragment

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>(),
    OrderDetailsActionListener {

    override val contentLayoutId = R.layout.fragment_order_details

    private val args: OrderDetailsFragmentArgs by navArgs()
    private val viewModel: OrderDetailsViewModel by lazy {
        obtainViewModel(OrderDetailsViewModel::class.java)
    }

    override fun setupBinding(binding: FragmentOrderDetailsBinding) {
        binding.viewModel = viewModel
        binding.listener = this
        binding.rvPositions.addItemDecoration(OrderPositionsDecorator())
    }

    override fun setupViewModel() {
        viewModel.start(args.id)
    }

    override fun onEditButtonClick() {
        findNavController().navigate(OrderDetailsFragmentDirections.actionToOrderEdit(args.id))
    }
}