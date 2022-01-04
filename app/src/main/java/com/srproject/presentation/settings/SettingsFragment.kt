package com.srproject.presentation.settings

import android.content.Intent
import android.provider.DocumentsContract
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
        binding.viewModel = this.viewModel
        binding.listener = this
    }

    override fun setupViewModel() {
        viewModel.apply {
            showDataNotEmptyError.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, R.string.error_not_empty_data, Toast.LENGTH_LONG).show()
            })
            showFileLoadError.observe(viewLifecycleOwner, Observer {
                Toast.makeText(context, R.string.error_file_data_load, Toast.LENGTH_LONG).show()
            })
        }
    }

    override fun setupViews() {

    }

    override fun onSaveDataToFileClick() {
        viewModel.onSaveDataToFileClicked()
    }

    override fun onRestoreDataFromFileClick() {
        //viewModel.onRestoreDataFromFileClicked()
        openFile()
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "text/plain"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            //putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data?.data?.run {
            viewModel.onRestoreDataFromFileClicked(this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}