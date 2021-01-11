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

    inline fun <reified T : Any> flow(key: String, defaultValue: T) = dataStore.data.map {
        var needConverter = false
        val preferencesKey = when (T::class) {
            Int::class, String::class, Boolean::class, Float::class, Long::class, Double::class -> preferencesKey<T>(
                key
            )
            else -> {
                needConverter = true
                preferencesKey<String>(key)
            }
        }
        val value = it[preferencesKey]
        if (value == null) {
            defaultValue
        } else {
            if (needConverter) converter?.deserialize(value.toString(), T::class.java) else value
        }
    }

    suspend inline fun <reified T : Any> get(key: String, defaultValue: T) =
        flow(key, defaultValue).first()

    suspend inline fun <reified T : Any> set(key: String, value: T) {
        when (T::class) {
            Int::class, String::class, Boolean::class, Float::class, Long::class, Double::class -> {
                dataStore.edit {
                    it[preferencesKey<T>(key)] = value
                }
            }
            else -> {
                val stringValue = this.converter?.serialize(value) ?: ""
                dataStore.edit {
                    it[preferencesKey<String>(key)] = stringValue
                }
            }
        }

    }

}