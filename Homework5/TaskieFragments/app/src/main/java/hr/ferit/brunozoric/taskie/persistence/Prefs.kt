package hr.ferit.brunozoric.taskie.persistence

import android.preference.PreferenceManager
import hr.ferit.brunozoric.taskie.Taskie

object Prefs {

    val PRIORITY_KEY = "Priority Key"

    private fun sharedPrefs() = PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun storeInt(key: String, value: Int) {
        sharedPrefs().edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPrefs().getInt(key, defaultValue)
    }
}