package com.voidx.spectable.preferences

import kotlin.reflect.KProperty

interface LongStorageProperty {
    operator fun getValue(
        thisRef: Any?,
        property: KProperty<*>
    ): Long

    operator fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Long
    )
}
