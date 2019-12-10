package com.srproject.presentation.statistics

import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentStatisticsBinding
import com.srproject.presentation.BaseFragment

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>(), StatisticsActionListener {

    private val viewModel: StatisticsViewModel by lazy {
        obtainViewModel(StatisticsViewModel::class.java)
    }

    override val contentLayoutId = R.layout.fragment_statistics

    override fun setupBinding(binding: FragmentStatisticsBinding) {
        binding.viewModel = viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
        }
    }

    override fun onPreviousMonthClick() {
        viewModel.onPreviousMonthClicked()
    }

    override fun onNextMonthClick() {
        viewModel.onNextMonthClicked()
    }
}