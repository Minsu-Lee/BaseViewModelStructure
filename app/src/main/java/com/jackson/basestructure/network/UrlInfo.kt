package com.jackson.basestructure.network

object UrlInfo {

    private const val SSL = true
    private const val MAIN_DOMAIN = "jsonplaceholder.typicode.com"

    private fun getSSLDomain(domain: String): String {
        val ssl: String = if (SSL) "https://" else "http://"
        return "$ssl$domain"
    }

    val BASE_URL: String = getSSLDomain(MAIN_DOMAIN)

    // const val TODO = "/todo"
    // const val LIST = "/list"
    const val TODOS = "/todos"
    // /todo/list -> "$TODO$LIST"

    const val TODO_LIST = "$TODOS"

}