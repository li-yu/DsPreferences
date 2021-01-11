package cn.liyuyu.datastoreext.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.*

/**
 * Created by liyu on 2021/01/10 19:34.
 */
object DsPreferences {

    private lateinit var name: String
    private lateinit var context: Context
    var converter: Converter? = null

    val dataStore by lazy {
        context.createDataStore(name)
    }

    fun init(context: Context, name: String = "DsPreferences", converter: Converter? = null) {
        this.context = context.applicationContext
        this.name = name
        this.converter = converter
    }

    inline fun <reified T : Any> flow(key: String, defaultValue: T? = null) = dataStore.data.map {
        when (T::class) {
            Int::class, String::class, Boolean::class, Float::class, Long::class, Double::class -> {
                it[preferencesKey<T>(key)] ?: defaultValue
            }
            else -> {
                converter?.deserialize(
                    it[preferencesKey(key)] ?: "",
                    T::class.java
                )
            }
        }
    }

    suspend inline fun <reified T : Any> get(key: String, defaultValue: T? = null) =
        flow(key, defaultValue).first()

    suspend inline fun <reified T : Any> set(key: String, value: T) {
        when (T::class) {
            Int::class, String::class, Boolean::class, Float::class, Long::class, Double::class -> {
                dataStore.edit {
                    it[preferencesKey<T>(key)] = value
                }
            }
            else -> {
                dataStore.edit {
                    it[preferencesKey<String>(key)] = this.converter?.serialize(value) ?: ""
                }
            }
        }

    }

}