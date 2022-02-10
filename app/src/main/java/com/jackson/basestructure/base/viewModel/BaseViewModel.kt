package com.jackson.basestructure.base.viewModel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.jackson.basestructure.BaseApplication
import com.jackson.basestructure.base.coroutine.BaseCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(private val dispatchers: CoroutineContext = Dispatchers.Main): ViewModel(), BaseCoroutineScope {
    lateinit var applicationContext: BaseApplication
    lateinit var lifecycleOwner: LifecycleOwner
    val lifecycleScope: LifecycleCoroutineScope by lazy { lifecycleOwner.lifecycleScope }

    override val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    // BaseViewModelActivity 에서 onDestroy 시점에 호출
    override fun releaseCoroutine() {
        job.cancel()
    }

    fun initViewModel(lifecycleOwner: LifecycleOwner) {
        this.applicationContext = BaseApplication.getApplicationContext()
        this.lifecycleOwner = lifecycleOwner
    }
}