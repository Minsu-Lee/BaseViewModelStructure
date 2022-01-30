package com.jackson.basestructure

import androidx.multidex.MultiDexApplication

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
    }
}