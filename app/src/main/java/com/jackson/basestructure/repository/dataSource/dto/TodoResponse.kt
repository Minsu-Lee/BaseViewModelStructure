package com.jackson.basestructure.repository.dataSource.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jackson.basestructure.repository.model.Todo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoResponse(var results: List<TodoDto>) : Parcelable {

    @Parcelize
    data class TodoDto(@SerializedName("userId")
                       var userId: Int,
                       @SerializedName("id")
                       var id: Int,
                       @SerializedName("title")
                       var title: String,
                       @SerializedName("completed")
                       var completed: Boolean) : Parcelable {


    }
}

fun TodoResponse.TodoDto.convert(): Todo {
    return Todo(userId = this.userId,
        id = this.id,
        title = this.title,
        completed = this.completed)
}