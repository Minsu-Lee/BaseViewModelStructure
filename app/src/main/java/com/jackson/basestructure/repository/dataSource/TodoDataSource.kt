package com.jackson.basestructure.repository.dataSource

import com.jackson.basestructure.base.network.API
import com.jackson.basestructure.repository.dataSource.dao.TodoService
import com.jackson.basestructure.repository.dataSource.dto.TodoResponseDto

class TodoDataSource {

    suspend fun getTodoList(): List<TodoResponseDto.TodoDto> {
        return API.typicode(TodoService::class.java).getTodos()
    }
}