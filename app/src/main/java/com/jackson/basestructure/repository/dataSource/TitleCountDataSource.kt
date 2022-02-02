package com.jackson.basestructure.repository.dataSource

import com.jackson.basestructure.repository.dataSource.dao.TitleCountDao
import com.jackson.basestructure.repository.dataSource.dto.TitleCountEntity
import com.jackson.basestructure.repository.dataSource.dto.convert
import com.jackson.basestructure.repository.vo.TitleCount

class TitleCountDataSource(private val titleCountDao: TitleCountDao) {

    suspend fun getTitleCount(): TitleCount? {
        return titleCountDao.getTitleCountEntity()?.convert()
    }

    suspend fun setTitleCount(title: String, count: Int): TitleCount {
        with(titleCountDao) {
            if (getTitleCount() == null) {
                insertCount(TitleCountEntity(title, count))
            } else {
                updateTitleCount(title, count)
            }

            return getTitleCountEntity()?.convert()!!
        }
    }
}