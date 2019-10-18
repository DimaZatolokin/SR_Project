package com.srproject.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.srproject.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    protected lateinit var binding: B

    abstract val contentLayoutId: Int
        @LayoutRes get

    protected open fun setupBinding(binding: B) {}

    protected open fun setupViews() {}

    protected open fun setupViewModel() {}

    protected open fun setupToolbar() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)
        binding.lifecycleOwner = this
        setupBinding(binding)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        setupViewModel()
        setupToolbar()
    }

    protected fun showQuestionDialog(
        title: String = getString(R.string.dialog_title),
        message: String,
        actionAccept: () -> Unit,
        actionDecline: () -> Unit
    ) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(R.string.yes) { _, _ ->
                actionAccept.invoke()
            }
            setNegativeButton(R.string.no) { _, _ ->
                actionDecline.invoke()
            }
        }.show()
    }
}