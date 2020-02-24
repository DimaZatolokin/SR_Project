package com.srproject.presentation.settings

import android.widget.Toast
import androidx.lifecycle.Observer
import com.srproject.R
import com.srproject.common.obtainViewModel
import com.srproject.databinding.FragmentSettingsBinding
import com.srproject.presentation.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(), SettingsActionListener {

    private val viewModel: SettingsViewModel by lazy {
        obtainViewModel(SettingsViewModel::class.java)
    }

    override val contentLayoutId = R.layout.fragment_settings

    override fun setupBinding(binding: FragmentSettingsBinding) {
        binding.viewModel = viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.apply {
            start()
            showErrorCommand.observe(this@SettingsFragment, Observer {
                Toast.makeText(context, R.string.sync_error, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun onSyncClick() {
        showQuestionDialog(message = getString(R.string.sync_dialog_message), actionAccept = {
            viewModel.onSyncClicked()
        })
    }
}