package cn.liyuyu.datastoreext.core

interface Converter {

    fun from(value: Any): String

    fun <T> to(serialized: String, clazz: Class<T>): T?
}