package com.jackson.basestructure.base.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface BaseCoroutineScope : CoroutineScope {

    val job: Job

    /**
     * Coroutine job cancel
     */
    fun releaseCoroutine()
}