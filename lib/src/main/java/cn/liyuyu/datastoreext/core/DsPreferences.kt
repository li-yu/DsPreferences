package cn.liyuyu.datastoreext.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by liyu on 2021/01/10 19:34.
 */
object DsPreferences {

    private lateinit var mName: String
    private lateinit var mContext: Context
    val dataStore by lazy {
        mContext.createDataStore(name = mName)
    }

    fun init(context: Context, name: String = "DsPreferences") {
        mContext = context.applicationContext
        mName = name
    }

    suspend inline fun <reified T : Any> get(key: String, defaultValue: T) = dataStore.data.map {
        it[preferencesKey<T>(key)] ?: defaultValue
    }.first()

    suspend inline fun <reified T : Any> set(key: String, value: T) =
        dataStore.edit {
            it[preferencesKey<T>(key)] = value
        }

}