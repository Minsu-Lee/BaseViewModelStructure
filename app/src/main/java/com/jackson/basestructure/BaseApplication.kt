package com.jackson.basestructure

import androidx.multidex.MultiDexApplication
import com.jackson.basestructure.base.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: MultiDexApplication() {

    companion object {
        private lateinit var applicationContext: BaseApplication

        fun getApplicationContext(): BaseApplication {
            if (this::applicationContext.isInitialized) {
                return applicationContext
            } else {
                throw ClassNotFoundException("application is not initialize")
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        BaseApplication.applicationContext = this
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModules)
            modules(databaseModules)
            modules(serviceModules)
            modules(dataSourceModules)
            modules(repositoriesModules)
            modules(viewModels)
        }
    }
}