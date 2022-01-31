package com.jackson.basestructure.repository.dataSource

import com.jackson.basestructure.network.API
import com.jackson.basestructure.network.TodoService
import com.jackson.basestructure.repository.dataSource.dto.TodoResponse

class TodoDataSource {

    suspend fun getTodoList(): List<TodoResponse.TodoDto> {
        return API.typicode(TodoService::class.java).getTodos()
    }

}