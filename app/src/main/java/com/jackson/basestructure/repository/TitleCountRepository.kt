package com.jackson.basestructure.repository

import com.jackson.basestructure.repository.dataSource.TitleCountDataSource
import com.jackson.basestructure.repository.vo.TitleCount

class TitleCountRepository(private val titleCountDataSource: TitleCountDataSource) {

    suspend fun getTitleCount(): TitleCount? {
        return titleCountDataSource.getTitleCount()
    }

    suspend fun setTitleCount(title: String, count: Int): TitleCount {
        return titleCountDataSource.setTitleCount(title, count)
    }
}