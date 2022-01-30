package com.jackson.basestructure.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jackson.basestructure.BaseApplication
import com.jackson.basestructure.base.viewModel.BaseViewModel

// DataBinding, BaseActivity
abstract class BaseBindingActivity<V : ViewDataBinding>(@LayoutRes private val layoutRes: Int): AppCompatActivity() {

    private lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.onCreate(savedInstanceState)
        binding.onCreate()
    }

    open fun V.onCreate() = Unit
    open fun V.onCreate(savedInstanceState: Bundle?) = Unit
}

// DataBinding & ViewModel, BaseActivity
abstract class BaseViewModelActivity<T : ViewDataBinding, M: BaseViewModel>(@LayoutRes private val layoutRes: Int): BaseBindingActivity<T>(layoutRes) {

    abstract val viewModel: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initViewModel()
    }

    private fun BaseViewModel.initViewModel() {
        applicationContext = BaseApplication.getApplicationContext()
        lifecycleOwner = this@BaseViewModelActivity
    }
}
