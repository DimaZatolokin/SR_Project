package com.srproject.presentation.consumerDetails

import androidx.navigation.fragment.navArgs
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentConsumerDetailsBinding
import com.srproject.presentation.BaseFragment
import com.srproject.presentation.activeOdrersList.OrdersItemDecorator
import kotlinx.android.synthetic.main.fragment_history.*

class ConsumerDetailsFragment : BaseFragment<FragmentConsumerDetailsBinding>() {

    private val viewModel: ConsumerDetailsViewModel by lazy {
        obtainViewModel(ConsumerDetailsViewModel::class.java)
    }
    private val args: ConsumerDetailsFragmentArgs by navArgs()

    override val contentLayoutId = R.layout.fragment_consumer_details

    override fun setupBinding(binding: FragmentConsumerDetailsBinding) {
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.start(args.name)
    }

    override fun setupViews() {
        rvOrders.addItemDecoration(OrdersItemDecorator())
    }
}