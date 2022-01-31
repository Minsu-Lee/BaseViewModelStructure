package com.jackson.basestructure.base.koin

import com.jackson.basestructure.BaseApplication
import com.jackson.basestructure.database.TitleCountDatabase
import org.koin.dsl.module

// default instance
val databaseModules = module {

    single { TitleCountDatabase.getInstance(BaseApplication.getApplicationContext()) }

}