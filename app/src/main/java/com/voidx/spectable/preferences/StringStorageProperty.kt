package com.voidx.spectable.preferences

import kotlin.reflect.KProperty

interface StringStorageProperty {
    operator fun getValue(
            thisRef: Any?,
            property: KProperty<*>
    ): String?

    operator fun setValue(
            thisRef: Any?,
            property: KProperty<*>,
            value: String?
    )
}
