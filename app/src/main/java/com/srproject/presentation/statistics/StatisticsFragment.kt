package com.srproject.presentation.statistics

import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentStatisticsBinding
import com.srproject.presentation.BaseFragment

class StatisticsFragment : BaseFragment<FragmentStatisticsBinding>() {

    private val viewModel: StatisticsViewModel by lazy {
        obtainViewModel(StatisticsViewModel::class.java)
    }

    override val contentLayoutId = R.layout.fragment_statistics

    override fun setupBinding(binding: FragmentStatisticsBinding) {
        binding.viewModel = viewModel
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
        }
    }
}