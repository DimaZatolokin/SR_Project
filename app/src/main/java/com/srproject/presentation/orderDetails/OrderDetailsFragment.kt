package com.srproject.presentation.orderDetails

import androidx.navigation.fragment.navArgs
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentOrderDetailsBinding
import com.srproject.presentation.BaseFragment
import com.srproject.presentation.activeOdrersList.OrdersItemDecorator

class OrderDetailsFragment : BaseFragment<FragmentOrderDetailsBinding>() {

    override val contentLayoutId = R.layout.fragment_order_details

    private val args: OrderDetailsFragmentArgs by navArgs()
    private val viewModel: OrderDetailsViewModel by lazy {
        obtainViewModel(OrderDetailsViewModel::class.java)
    }

    override fun setupBinding(binding: FragmentOrderDetailsBinding) {
        binding.viewModel = viewModel
        binding.rvPositions.addItemDecoration(OrdersItemDecorator())
    }

    override fun setupViewModel() {
        viewModel.start(args.id)
    }
}