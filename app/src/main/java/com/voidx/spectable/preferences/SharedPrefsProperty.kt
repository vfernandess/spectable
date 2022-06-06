package com.voidx.spectable.preferences

import com.orhanobut.hawk.Hawk
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

enum class Ephemerality {
    ALL_SESSION_LONG,
    RENEWABLE_IN_SESSION
}

abstract class SharedPrefsProperty<T>(
        private val namespace: String,
        default: T,
        private val ephemerality: Ephemerality = Ephemerality.RENEWABLE_IN_SESSION,
) : ReadWriteProperty<Any?, T> {

    private var currentValue: T = Hawk.get(namespace, default)

    override fun getValue(
            thisRef: Any?,
            property: KProperty<*>
    ): T = currentValue

    override fun setValue(
            thisRef: Any?,
            property: KProperty<*>,
            value: T
    ) {
        if (currentValue != value) {
            persistData(value)
            if (ephemerality == Ephemerality.RENEWABLE_IN_SESSION) {
                currentValue = value
            }
        }
    }

    private fun persistData(value: T) = if (value == null)
        Hawk.delete(namespace)
    else
        Hawk.put(namespace, value)
}
