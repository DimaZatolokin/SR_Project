package com.srproject.presentation.consumers

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentConsumersBinding
import com.srproject.presentation.BaseFragment
import com.srproject.presentation.activeOdrersList.OrdersItemDecorator
import kotlinx.android.synthetic.main.fragment_consumers.*

class ConsumersFragment : BaseFragment<FragmentConsumersBinding>() {

    val viewModel: ConsumersViewModel by lazy {
        obtainViewModel(ConsumersViewModel::class.java)
    }

    override val contentLayoutId = R.layout.fragment_consumers

    override fun setupBinding(binding: FragmentConsumersBinding) {
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
            navigateToConsumerDetailsCommand.observe(this@ConsumersFragment, Observer {
                findNavController().navigate(ConsumersFragmentDirections.actionToConsumerDetails(it))
            })
        }
    }

    override fun setupViews() {
        rvConsumers.addItemDecoration(OrdersItemDecorator())
    }
}