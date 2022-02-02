package com.jackson.basestructure.repository.dataSource.dao

import com.jackson.basestructure.base.network.UrlInfo
import com.jackson.basestructure.repository.dataSource.dto.TodoResponseDto
import retrofit2.http.GET

interface TodoService {

    @GET(UrlInfo.TODO_LIST)
    suspend fun getTodos(): List<TodoResponseDto.TodoDto>

}