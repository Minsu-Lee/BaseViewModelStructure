package com.jackson.basestructure.base.viewModel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.jackson.basestructure.BaseApplication

abstract class BaseViewModel: ViewModel() {
    lateinit var applicationContext: BaseApplication
    lateinit var lifecycleOwner: LifecycleOwner

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