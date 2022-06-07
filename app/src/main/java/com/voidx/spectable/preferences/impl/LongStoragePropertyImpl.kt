package com.voidx.spectable.preferences.impl

import com.voidx.spectable.preferences.Ephemerality
import com.voidx.spectable.preferences.LongStorageProperty
import com.voidx.spectable.preferences.SharedPrefsProperty
import kotlin.properties.ReadWriteProperty

class LongStoragePropertyImpl(
    namespace: String,
    default: Long,
    ephemerality: Ephemerality = Ephemerality.RENEWABLE_IN_SESSION
) : SharedPrefsProperty<Long>(namespace, default, ephemerality),
    ReadWriteProperty<Any?, Long>,
    LongStorageProperty
