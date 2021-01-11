package cn.liyuyu.datastoreext.core

interface Converter {

    fun serialize(value: Any): String

    fun <T> deserialize(serialized: String, clazz: Class<T>): T?
}