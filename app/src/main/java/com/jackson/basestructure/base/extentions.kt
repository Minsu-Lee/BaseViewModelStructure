package com.jackson.basestructure.base

import android.view.View
import android.widget.Toast
import com.jackson.basestructure.BaseApplication

private val ctx: BaseApplication
    get() = BaseApplication.getApplicationContext()

fun String.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(ctx, this, duration).also { it.show() }
}

@JvmName("toast1")
fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return msg.toast(duration)
}

fun <T> List<T>.toArrayList(): ArrayList<T> = ArrayList(this)

fun <T> List<T>.isVisible() = if (size > 0) View.VISIBLE else View.GONE

fun <T> List<T>.isEmptyGuideVisible() = if (size > 0) View.GONE else View.VISIBLE