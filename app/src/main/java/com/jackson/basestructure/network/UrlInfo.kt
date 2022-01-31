package com.jackson.basestructure.network

object UrlInfo {

    private const val SSL = true
    private const val MAIN_DOMAIN = "jsonplaceholder.typicode.com"

    private fun getDomain(): String {
        val url: String = if (SSL) "https://" else "http://"
        return "$url$MAIN_DOMAIN"
    }

    val BASE_URL: String = getDomain()

    const val TODO = "/todo"
    const val LIST = "/list"
    const val TODOS = "/todos"

    // /todo/list -> "$TODO$LIST"
    const val TODO_LIST = "$TODOS"

}