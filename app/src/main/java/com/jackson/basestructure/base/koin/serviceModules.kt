package com.jackson.basestructure.base.koin

import com.jackson.basestructure.base.network.API
import com.jackson.basestructure.repository.dataSource.dao.TodoService
import org.koin.dsl.module

val serviceModules = module {
    factory { API.typicode(TodoService::class.java) }
}