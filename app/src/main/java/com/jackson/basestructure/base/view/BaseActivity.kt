package com.jackson.basestructure.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jackson.basestructure.base.coroutine.BaseCoroutineScope
import com.jackson.basestructure.base.string
import com.jackson.basestructure.base.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// init CoroutineScope, BaseActivity
abstract class BaseActivity(private val dispatchers: CoroutineContext = Dispatchers.Main): AppCompatActivity(), BaseCoroutineScope {

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

// DataBinding, BaseActivity
abstract class BaseDataBindingActivity<V : ViewDataBinding>(@LayoutRes private val layoutRes: Int): BaseActivity() {

    protected lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
        binding.lifecycleOwner = this
        onCreate()
    }

    open fun onCreate() = Unit

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun setActionBarTitle(@StringRes titleRes: Int) {
        setActionBarTitle(string(titleRes))
    }
}

// DataBinding & ViewModel, BaseActivity
abstract class BaseViewModelActivity<V : ViewDataBinding, M: BaseViewModel>(@LayoutRes private val layoutRes: Int): BaseDataBindingActivity<V>(layoutRes) {

    abstract val vm: M

    override fun onCreate(savedInstanceState: Bundle?) {
        vm.initViewModel(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        vm.releaseCoroutine()
    }
}