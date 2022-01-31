package com.jackson.basestructure.base.koin

import com.jackson.basestructure.repository.TodoRepository
import org.koin.dsl.module

val repositoriesModules = module {
    factory { TodoRepository(get()) }
}