package com.jackson.basestructure.repository.dataSource.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jackson.basestructure.repository.vo.TitleCount

@Entity(tableName = "title_count_table")
data class TitleCountEntity(var title: String,
                            var count: Int) {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

fun TitleCountEntity.convert(): TitleCount {
    return TitleCount(id = this.id, title = this.title, count = this.count)
}