package com.jackson.basestructure.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.observe
import com.jackson.basestructure.BaseApplication
import com.jackson.basestructure.base.coroutine.BaseCoroutineScope
import com.jackson.basestructure.base.viewModel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

// init CoroutineScope, BaseFragment
abstract class BaseDialogFragment(private val dispatchers: CoroutineContext = Dispatchers.Main): DialogFragment(),
    BaseCoroutineScope {

    override val job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job

    override fun releaseCoroutine() {
        job.cancel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAutoCleared()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseCoroutine()
    }

    /**
     * destroy 시점에 BaseViewBindingDialogFragment를 상속받는 dialog가 닫혀있지 않다면
     * dismissAllowingStateLoss()를 호출합니다.
     */
    private fun initAutoCleared() {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewLifecycleOwnerLiveData.observe(this@BaseDialogFragment) { viewLifecycleOwner ->
                    viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            this@BaseDialogFragment.dismissAllowingStateLoss()
                        }
                    })
                }
            }
        })
    }

    /**
     * Dialog의 크기를 지정합니다.
     */
    fun initDialogViewSize(width: Int = 0, height: Int = 0) = dialog?.window?.setLayout(width, height)


    /**
     * show()가 호출되는 경우, 내부에서
     * FragmentTransaction의 commit이 호출되는데,
     * onSaveInstanceState 함수가 호출된 상태에서는 IllegalStateException을 발생시킵니다.
     * ( https://beginner97.tistory.com/2 )
     */
    fun showAllowingStateLoss(fm: FragmentManager, tag: String = "") {
        fm.beginTransaction().add(this, tag)
            .commitAllowingStateLoss()
    }
}

// DataBinding, BaseFragment
abstract class BaseBindingDialogFragment<V : ViewDataBinding>(@LayoutRes private val layoutRes: Int): BaseDialogFragment() {

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
abstract class BaseViewModelDialogFragment<V : ViewDataBinding, M: BaseViewModel>(@LayoutRes private val layoutRes: Int): BaseBindingDialogFragment<V>(layoutRes) {

    abstract val viewModel: M

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            viewModel.initViewModel()
        }
    }

    private fun BaseViewModel.initViewModel() {
        applicationContext = BaseApplication.getApplicationContext()
        lifecycleOwner = this@BaseViewModelDialogFragment
    }
}