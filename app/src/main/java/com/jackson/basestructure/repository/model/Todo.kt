package com.jackson.basestructure.repository.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(var userId: Int = -1,
                var id: Int = -1,
                var title: String = "",
                var completed: Boolean = false): Parcelable