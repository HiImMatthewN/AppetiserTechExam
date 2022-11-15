package com.nantesmatthew.user_session.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nantesmatthew.user_session.domain.model.Screen
import com.nantesmatthew.user_session.domain.model.UserSession
import java.util.*


@Entity(tableName = "user_session_table")
class UserSessionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "last_opened")
    val lastOpened: Long,
    @ColumnInfo(name = "screen_opened")
    val screenOpened: String,
    @ColumnInfo(name = "screen_info")
    val screenInfo:Int

)

fun UserSessionEntity.toDomain(): UserSession {
    return UserSession(
        lastOpened = Calendar.getInstance().apply {
            timeInMillis = lastOpened
        }.time,
        screenOpened = when(screenOpened){
            Screen.Category.name -> Screen.Category
            Screen.Detail.name -> Screen.Detail
            else -> Screen.Invalid
        },
        screenInfo = this.screenInfo
    )
}