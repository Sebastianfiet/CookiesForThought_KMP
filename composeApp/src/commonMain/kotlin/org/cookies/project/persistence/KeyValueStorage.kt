package org.cookies.project.persistence

interface KeyValueStorage {
    fun getInt(key: String, default: Int = 0): Int
    fun putInt(key: String, value: Int)

    fun getLong(key: String, default: Long = 0L): Long
    fun putLong(key: String, value: Long)

    fun getBoolean(key: String, default: Boolean = false): Boolean
    fun putBoolean(key: String, value: Boolean)
}
