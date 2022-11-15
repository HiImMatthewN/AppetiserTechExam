package com.nantesmatthew.user_session.data.module

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nantesmatthew.user_session.domain.model.Screen

class Converters {


    @TypeConverter
    fun screenToString(screen: Screen){
        when(screen){
            is Screen.Category -> Gson().toJson(screen)
            is Screen.Detail -> Gson().toJson(screen)
            else -> {
                ""
            }
        }
    }

    @TypeConverter
    fun stringToScreen(string: String) = Gson().fromJson(string, Screen::class.java)


}