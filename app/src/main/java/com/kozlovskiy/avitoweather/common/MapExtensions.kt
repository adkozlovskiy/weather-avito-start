package com.kozlovskiy.avitoweather.common

fun <K, V> Map<K, V>.getOr(key: K, default: V): V {
    return getOrElse(key) { default }
}