package org.cookies.project.persistence

import java.util.prefs.Preferences

class DesktopKeyValueStorage : KeyValueStorage {

    private val prefs: Preferences =
        Preferences.userRoot().node("cookies_for_thought_prefs")

    override fun getInt(key: String, default: Int): Int =
        prefs.getInt(key, default)

    override fun putInt(key: String, value: Int) {
        prefs.putInt(key, value)
    }

    override fun getLong(key: String, default: Long): Long =
        prefs.getLong(key, default)

    override fun putLong(key: String, value: Long) {
        prefs.putLong(key, value)
    }

    override fun getBoolean(key: String, default: Boolean): Boolean =
        prefs.getBoolean(key, default)

    override fun putBoolean(key: String, value: Boolean) {
        prefs.putBoolean(key, value)
    }
}
