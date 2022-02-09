package com.jackson.basestructure.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.jackson.basestructure.BaseApplication

private val ctx: BaseApplication
    get() = BaseApplication.getApplicationContext()

fun string(@StringRes strRes: Int): String = ctx.getString(strRes)

fun String.toast(duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(ctx, this, duration).also { it.show() }
}

@JvmName("toast1")
fun toast(msg: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return msg.toast(duration)
}

fun @receiver:StringRes Int.toast(duration: Int = Toast.LENGTH_SHORT): Toast = string(this).toast(duration)

@JvmName("toast2")
fun toast(@StringRes msgRes: Int, duration: Int = Toast.LENGTH_SHORT): Toast {
    return string(msgRes).toast(duration)
}

fun <T> List<T>.toArrayList(): ArrayList<T> = ArrayList(this)

fun <T> LifecycleOwner.observer(liveData: LiveData<T>, block: (T)->Unit) {
    liveData.observe(this, Observer(block))
}