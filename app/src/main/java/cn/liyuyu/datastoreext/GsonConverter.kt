package cn.liyuyu.datastoreext

import cn.liyuyu.datastoreext.core.Converter
import com.google.gson.Gson

class GsonConverter(val gson: Gson) : Converter {
    override fun serialize(value: Any): String {
        return gson.toJson(value)
    }

    override fun <T> deserialize(serialized: String, clazz: Class<T>): T? {
        return gson.fromJson(serialized, clazz)
    }
}