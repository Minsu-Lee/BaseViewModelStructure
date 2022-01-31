package com.jackson.basestructure.base.koin

import com.jackson.basestructure.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { MainViewModel(get()) }
}