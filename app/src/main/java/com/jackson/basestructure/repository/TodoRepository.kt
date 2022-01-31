package com.jackson.basestructure.repository

import com.jackson.basestructure.repository.dataSource.TodoDataSource
import com.jackson.basestructure.repository.dataSource.dto.TodoResponse
import com.jackson.basestructure.repository.dataSource.dto.convert
import com.jackson.basestructure.repository.model.Todo

class TodoRepository(private val todoDataSource: TodoDataSource) {

    suspend fun getTodoList(): List<Todo> {
        return todoDataSource.getTodoList()
            .map(TodoResponse.TodoDto::convert)
    }
}