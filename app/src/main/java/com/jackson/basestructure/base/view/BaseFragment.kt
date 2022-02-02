package com.jackson.basestructure.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jackson.basestructure.base.coroutine.BaseCoroutineScope
import com.jackson.basestructure.base.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// init CoroutineScope, BaseFragment
abstract class BaseFragment(private val dispatchers: CoroutineContext = Dispatchers.Main): Fragment(), BaseCoroutineScope {

    override val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    override fun releaseCoroutine() {
        job.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCoroutine()
    }
}

// DataBinding, BaseFragment
abstract class BaseBindingFragment<V : ViewDataBinding>(@LayoutRes private val layoutRes: Int): BaseFragment() {

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
abstract class BaseViewModelFragment<V : ViewDataBinding, M: BaseViewModel>(@LayoutRes private val layoutRes: Int): BaseBindingFragment<V>(layoutRes) {

    abstract val viewModel: M

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            viewModel.initViewModel(this@BaseViewModelFragment)
        }
    }
}