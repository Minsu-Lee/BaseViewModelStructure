package com.jackson.basestructure.repository.dataSource

import com.jackson.basestructure.repository.dataSource.dao.TodoService
import com.jackson.basestructure.repository.dataSource.dto.TodoResponseDto

class TodoDataSource(private val todoService: TodoService) {

    suspend fun getTodoList(): List<TodoResponseDto.TodoDto> {
        return todoService.getTodos()
    }
}