package com.example.robogyan.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.robogyan.SupabaseClientProvider
import io.github.jan.supabase.auth.auth
import androidx.core.content.edit

object SharedPrefManager {

    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"
    private const val KEY_REMOTE_VERSION = "remote_version"
    private const val KEY_ADD_VERSION = "add_version"
    private const val KEY_USER_ID = "user_id"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setLoggedIn(context: Context, isLoggedIn: Boolean) {
        getPrefs(context).edit { putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) }
    }

    fun isLoggedIn(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setRemoteVersion(context: Context, version: Int) {
        getPrefs(context).edit { putInt(KEY_REMOTE_VERSION, version) }
    }

    fun getRemoteVersion(context: Context): Int {
        return getPrefs(context).getInt(KEY_REMOTE_VERSION, 0)
    }

    fun setAddVersion(context: Context, version: Int) {
        getPrefs(context).edit { putInt(KEY_ADD_VERSION, version) }
    }

    fun getAddVersion(context: Context): Int {
        return getPrefs(context).getInt(KEY_ADD_VERSION, 0)
    }

    fun setUserId(context: Context) {
        val userId = SupabaseClientProvider.client.auth.currentUserOrNull()?.id
        if (userId != null) {
            getPrefs(context).edit { putString(KEY_USER_ID, userId) }
        }
    }

    fun getUserId(context: Context): String? {
        return getPrefs(context).getString(KEY_USER_ID, null)
    }

    fun clear(context: Context) {
        getPrefs(context).edit { clear() }
    }
}
