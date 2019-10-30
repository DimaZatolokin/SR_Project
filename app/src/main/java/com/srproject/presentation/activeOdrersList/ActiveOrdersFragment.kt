package com.srproject.presentation.activeOdrersList

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentActiveOrdersBinding
import com.srproject.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_active_orders.*

class ActiveOrdersFragment : BaseFragment<FragmentActiveOrdersBinding>(),
    ActiveOrderListActionListener {

    override val contentLayoutId = R.layout.fragment_active_orders

    private val viewModel: ActiveOrderListViewModel by lazy {
        obtainViewModel(ActiveOrderListViewModel::class.java)
    }

    override fun setupViews() {
        setHasOptionsMenu(true)
        rvActiveOrders.addItemDecoration(OrdersItemDecorator())
    }

    override fun setupBinding(binding: FragmentActiveOrdersBinding) {
        binding.viewModel = this.viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.start()
        viewModel.navigateToDetailsEvent.observe(this, Observer {
            findNavController().navigate(ActiveOrdersFragmentDirections.actionToOrderDetails(it))
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_menu -> {

            }
            R.id.item_filter -> {
                viewModel.filterClicked()
            }
            R.id.item_menu_consumers -> {

            }
            R.id.item_menu_products -> {
                findNavController().navigate(ActiveOrdersFragmentDirections.actionToProducts())
            }
            R.id.item_menu_settings -> {
                findNavController().navigate(ActiveOrdersFragmentDirections.actionToSettings())
            }
            R.id.item_menu_history -> {
                findNavController().navigate(ActiveOrdersFragmentDirections.actionToHistory())
            }
            R.id.item_menu_statistics -> {
                findNavController().navigate(ActiveOrdersFragmentDirections.actionToStatistics())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.getItem(0).icon =
            context?.getDrawable(if (viewModel.isFilterApplied) R.drawable.ic_filter_filled else R.drawable.ic_filter_white)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onAddOrderClick() {
        findNavController().navigate(ActiveOrdersFragmentDirections.actionToOrderCreate())
    }

    override fun onFilterDoneClicked() {
        viewModel.onFilterDoneClicked()
        activity?.invalidateOptionsMenu()
    }

    override fun onFilterPaidClicked() {
        viewModel.onFilterPaidClicked()
        activity?.invalidateOptionsMenu()
    }
}