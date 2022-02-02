package com.jackson.basestructure.repository.vo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TitleCount(var id: Int = 0,
                      var title: String = "",
                      var count: Int = 0): Parcelable