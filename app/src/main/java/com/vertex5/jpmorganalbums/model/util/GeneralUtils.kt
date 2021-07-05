package com.vertex5.jpmorganalbums.model.util

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object GeneralUtils {

    private const val APP_PREFS_NAME = "jpmorgan"
    private var appPref: SharedPreferences? = null

    private var uiHandler: Handler? = null
    @JvmStatic
    fun message(context: Context, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @JvmStatic
    fun getAppPref(context: Context): SharedPreferences? {
        if (appPref == null) appPref = context.getSharedPreferences(
            APP_PREFS_NAME,
            Context.MODE_PRIVATE
        )
        return appPref
    }

    @JvmStatic
    fun getHandler(): Handler? {
        if (uiHandler== null){
            uiHandler = Handler(Looper.getMainLooper())
        }
        return uiHandler
    }
}