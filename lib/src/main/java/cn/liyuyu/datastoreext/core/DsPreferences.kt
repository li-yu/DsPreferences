package cn.liyuyu.datastoreext.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.*

/**
 * Created by liyu on 2021/01/10 19:34.
 */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DsPreferences")

class DsPreferences(val context: Context) {

    companion object {
        var converter: Converter? = null
    }

    inline fun <reified T : Any> flow(key: String, defaultValue: T? = null) =
        context.dataStore.data.map {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)] ?: defaultValue
                }
                String::class -> {
                    it[stringPreferencesKey(key)] ?: defaultValue
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)] ?: defaultValue
                }
                Float::class -> {
                    it[floatPreferencesKey(key)] ?: defaultValue
                }
                Long::class -> {
                    it[longPreferencesKey(key)] ?: defaultValue
                }
                Double::class -> {
                    it[doublePreferencesKey(key)] ?: defaultValue
                }
                else -> {
                    converter?.deserialize(
                        it[stringPreferencesKey(key)] ?: "",
                        T::class.java
                    )
                }
            }
        }

    suspend inline fun <reified T : Any> get(key: String, defaultValue: T? = null) =
        flow(key, defaultValue).first()

    suspend inline fun <reified T : Any> set(key: String, value: T) {
        context.dataStore.edit {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)] = value as Int
                }
                String::class -> {
                    it[stringPreferencesKey(key)] = value as String
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)] = value as Boolean
                }
                Float::class -> {
                    it[floatPreferencesKey(key)] = value as Float
                }
                Long::class -> {
                    it[longPreferencesKey(key)] = value as Long
                }
                Double::class -> {
                    it[doublePreferencesKey(key)] = value as Double
                }
                else -> {
                    it[stringPreferencesKey(key)] = converter?.serialize(value) ?: ""
                }
            }
        }

    }

}