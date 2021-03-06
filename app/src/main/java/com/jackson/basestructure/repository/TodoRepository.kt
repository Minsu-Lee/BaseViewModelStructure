package com.jackson.basestructure.repository

import com.jackson.basestructure.repository.dataSource.TodoDataSource
import com.jackson.basestructure.repository.dataSource.dto.TodoResponseDto
import com.jackson.basestructure.repository.dataSource.dto.convert
import com.jackson.basestructure.repository.vo.Todo

class TodoRepository(private val todoDataSource: TodoDataSource) {

    suspend fun getTodoList(): List<Todo> {
        return todoDataSource.getTodoList()
            .map(TodoResponseDto.TodoDto::convert)
    }
}