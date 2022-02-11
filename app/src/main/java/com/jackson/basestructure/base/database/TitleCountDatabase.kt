package com.jackson.basestructure.base.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jackson.basestructure.repository.dataSource.dao.TitleCountDao
import com.jackson.basestructure.repository.dataSource.dto.TitleCountEntity

@Database(entities = [TitleCountEntity::class], version = 1, exportSchema = false)
abstract class TitleCountDatabase: RoomDatabase() {

    abstract fun titleCountDao(): TitleCountDao

    companion object {
        const val DATABASE_NAME = "title-count-database"
        const val TITLE_COUNT_TABLE_NAME = "title_count_table"

        fun createInstance(context: Context): TitleCountDatabase {
            return Room.databaseBuilder(context.applicationContext,
                TitleCountDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}