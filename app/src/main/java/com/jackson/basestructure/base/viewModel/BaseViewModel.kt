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

    override fun releaseCoroutine() {
        job.cancel()
    }

    fun initViewModel(lifecycleOwner: LifecycleOwner) {
        this.applicationContext = BaseApplication.getApplicationContext()
        this.lifecycleOwner = lifecycleOwner
    }

    //    // RxJava, 사용하는 경우
//    private val compositeDisposable = CompositeDisposable()
//
//    fun addDisposable(disposable: Disposable) {
//        compositeDisposable.add(disposable)
//    }
//
//    override fun onCleared() {
//        compositeDisposable.clear()
//        super.onCleared()
//    }
}