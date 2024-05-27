package com.example.ui_browser_setting

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREF_KEY = "my_shared_preferences_key"
        private const val LIST_KEY = "my_list_key"
    }

    fun getList(): MutableList<String> {
        val serializedList = sharedPreferences.getString(LIST_KEY, null)
        return serializedList?.split(",")?.toMutableList() ?: mutableListOf()
    }

    fun saveList(list: List<String>) {
        val serializedList = list.joinToString(",")
        sharedPreferences.edit().putString(LIST_KEY, serializedList).apply()
    }
}
