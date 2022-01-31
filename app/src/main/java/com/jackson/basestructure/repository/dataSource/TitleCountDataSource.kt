package com.jackson.basestructure.repository.dataSource

import com.jackson.basestructure.database.TitleCountDatabase
import com.jackson.basestructure.repository.dataSource.dto.TitleCountEntity
import com.jackson.basestructure.repository.dataSource.dto.convert
import com.jackson.basestructure.repository.model.TitleCount

class TitleCountDataSource(private val titleCountDatabase: TitleCountDatabase) {

    suspend fun getTitleCount(): TitleCount? {
        return titleCountDatabase.titleCountDao().getTitleCountEntity()?.convert()
    }

    suspend fun setTitleCount(title: String, count: Int): TitleCount {
        titleCountDatabase.titleCountDao().run {
            if (getTitleCount() == null) {
                insertCount(TitleCountEntity(title, count))
            } else {
                updateTitleCount(title, count)
            }

            return getTitleCountEntity()?.convert()!!
        }
    }
}