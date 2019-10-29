package com.srproject.presentation.history

import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentHistoryBinding
import com.srproject.presentation.BaseFragment
import com.srproject.presentation.activeOdrersList.OrdersItemDecorator
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(), HistoryActionListener {

    private val viewModel: HistoryViewModel by lazy {
        obtainViewModel(HistoryViewModel::class.java)
    }

    override val contentLayoutId = R.layout.fragment_history

    override fun setupBinding(binding: FragmentHistoryBinding) {
        binding.viewModel = this.viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
            navigateToDetailsEvent.observe(this@HistoryFragment, Observer {
                findNavController().navigate(HistoryFragmentDirections.actionToOrderDetails(it))
            })
        }
    }

    override fun setupViews() {
        rvOrders.addItemDecoration(OrdersItemDecorator())
    }

    override fun onItemClick(id: Long) {

    }
}