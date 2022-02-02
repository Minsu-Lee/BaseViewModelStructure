package com.jackson.basestructure.base.koin

import com.jackson.basestructure.repository.dataSource.TitleCountDataSource
import com.jackson.basestructure.repository.dataSource.TodoDataSource
import org.koin.dsl.module

val dataSourceModules = module {
    factory { TodoDataSource(get()) }
    factory { TitleCountDataSource(get()) }
}