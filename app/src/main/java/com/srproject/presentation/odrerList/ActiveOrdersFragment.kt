package com.srproject.presentation.odrerList

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentActiveOrdersBinding
import com.srproject.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_active_orders.*

class ActiveOrdersFragment : BaseFragment<FragmentActiveOrdersBinding>() {

    override val contentLayoutId = R.layout.fragment_active_orders

    private val viewModel: ActiveOrderListViewModel by lazy {
        obtainViewModel(ActiveOrderListViewModel::class.java)
    }

    override fun setupViews() {
        setHasOptionsMenu(true)
    }

    override fun setupBinding(binding: FragmentActiveOrdersBinding) {
        binding.viewModel = this.viewModel
    }

    override fun setupViewModel() {
        rvActiveOrders.addItemDecoration(OrdersItemDecorator())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_settings -> {
                findNavController(this).navigate(R.id.actionToSettings)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}