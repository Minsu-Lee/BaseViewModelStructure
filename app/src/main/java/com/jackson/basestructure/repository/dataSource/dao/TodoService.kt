package com.jackson.basestructure.repository.dataSource.dao

import com.jackson.basestructure.network.UrlInfo
import com.jackson.basestructure.repository.dataSource.dto.TodoResponse
import retrofit2.http.GET

interface TodoService {

    @GET(UrlInfo.TODO_LIST)
    suspend fun getTodos(): List<TodoResponse.TodoDto>

}