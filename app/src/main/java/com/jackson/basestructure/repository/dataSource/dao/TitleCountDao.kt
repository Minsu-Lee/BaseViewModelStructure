package com.jackson.basestructure.repository.dataSource.dao

import androidx.room.*
import com.jackson.basestructure.database.TitleCountDatabase
import com.jackson.basestructure.repository.dataSource.dto.TitleCountEntity

@Dao
interface TitleCountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCount(titleCount: TitleCountEntity)

    @Query("UPDATE ${TitleCountDatabase.TITLE_COUNT_TABLE_NAME} SET count = :count WHERE title = :title")
    suspend fun updateTitleCount(title: String, count: Int)

    @Query("SELECT * FROM ${TitleCountDatabase.TITLE_COUNT_TABLE_NAME}")
    suspend fun getTitleCountEntity(): TitleCountEntity?

}