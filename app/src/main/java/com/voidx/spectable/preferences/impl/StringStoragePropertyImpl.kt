package com.voidx.spectable.preferences.impl

import com.voidx.spectable.preferences.Ephemerality
import com.voidx.spectable.preferences.SharedPrefsProperty
import com.voidx.spectable.preferences.StringStorageProperty
import kotlin.properties.ReadWriteProperty

class StringStoragePropertyImpl(
        namespace: String,
        default: String? = null,
        ephemerality: Ephemerality = Ephemerality.RENEWABLE_IN_SESSION
) : SharedPrefsProperty<String?>(namespace, default, ephemerality),
        ReadWriteProperty<Any?, String?>,
        StringStorageProperty
