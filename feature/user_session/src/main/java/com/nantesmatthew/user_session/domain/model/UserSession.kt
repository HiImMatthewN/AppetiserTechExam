package com.nantesmatthew.user_session.domain.model

import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

data class UserSession(
    val lastOpened: Date,
    val screenOpened: Screen,
    val screenInfo: Int
) {

    fun getLastOpened(): String {
        return SimpleDateFormat().format(lastOpened).format("h:mm:ss")

    }


}

sealed class Screen(val name: String) {
    object Category : Screen("Category")
    object Detail : Screen("Detail")
    object Invalid : Screen("Invalid")
}