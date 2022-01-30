package com.jackson.basestructure.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jackson.basestructure.BaseApplication
import com.jackson.basestructure.base.viewModel.BaseViewModel

// DataBinding, BaseFragment
abstract class BaseBindingFragment<V : ViewDataBinding>(@LayoutRes private val layoutRes: Int): Fragment() {

    lateinit var binding: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding.onCreateView(inflater, container, savedInstanceState)
        binding.onCreateView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (this::binding.isInitialized) {
            binding.onViewCreated(view, savedInstanceState)
            binding.onViewCreated()
        }
    }

    open fun V.onCreateView() = Unit
    open fun V.onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = Unit

    open fun V.onViewCreated() = Unit
    open fun V.onViewCreated(view: View, savedInstanceState: Bundle?) = Unit
}

// DataBinding & ViewModel, BaseFragment
abstract class BaseViewModelFragment<T : ViewDataBinding, V: BaseViewModel>(@LayoutRes private val layoutRes: Int): Fragment() {

    abstract val viewModel: V

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            viewModel.initViewModel()
        }
    }

    private fun BaseViewModel.initViewModel() {
        applicationContext = BaseApplication.getApplicationContext()
        lifecycleOwner = this@BaseViewModelFragment
    }
}