package com.jackson.basestructure

import android.os.Bundle
import com.jackson.basestructure.base.view.BaseViewModelActivity
import com.jackson.basestructure.base.viewModel.BaseViewModel
import com.jackson.basestructure.databinding.ActivityMainBinding

class MainActivity : BaseViewModelActivity<ActivityMainBinding, BaseViewModel>(R.layout.activity_main) {

    override val viewModel: BaseViewModel = object: BaseViewModel() {}

    override fun ActivityMainBinding.onCreate(savedInstanceState: Bundle?) {

    }
}