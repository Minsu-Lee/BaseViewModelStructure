package com.jackson.basestructure.network

import com.jackson.basestructure.repository.dataSource.dto.TodoResponse
import retrofit2.http.GET

interface TodoService {

    @GET(UrlInfo.TODO_LIST)
    suspend fun getTodos(): List<TodoResponse.Todo>

}