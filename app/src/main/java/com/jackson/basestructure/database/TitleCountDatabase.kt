package com.jackson.basestructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jackson.basestructure.repository.dataSource.dao.TitleCountDao
import com.jackson.basestructure.repository.dataSource.dto.TitleCountEntity

@Database(entities = [TitleCountEntity::class], version = 1)
abstract class TitleCountDatabase: RoomDatabase() {

    abstract fun titleCountDao(): TitleCountDao

    companion object {
        const val DATABASE_NAME = "title-count-database"
        const val TITLE_COUNT_TABLE_NAME = "title_count_table"
        private var _instance: TitleCountDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TitleCountDatabase {
            return _instance ?: synchronized(TitleCountDatabase::class) {
                Room.databaseBuilder(context.applicationContext,
                    TitleCountDatabase::class.java,
                    DATABASE_NAME
                ).build().also { _instance =  it }
            }
        }
    }
}