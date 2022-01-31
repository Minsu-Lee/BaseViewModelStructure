package com.jackson.basestructure.repository.dataSource.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoResponse(var results: List<Todo>): Parcelable {

    @Parcelize
    data class Todo(@SerializedName("userId")
                    var userId: Int,
                    @SerializedName("id")
                    var id: Int,
                    @SerializedName("title")
                    var title: String,
                    @SerializedName("completed")
                    var completed: Boolean): Parcelable
}