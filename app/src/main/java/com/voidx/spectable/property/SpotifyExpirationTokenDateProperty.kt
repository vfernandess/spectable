package com.voidx.spectable.property

import com.voidx.spectable.preferences.LongStorageProperty
import com.voidx.spectable.preferences.impl.LongStoragePropertyImpl

private const val KEY = "expiration-token-date"

class SpotifyExpirationTokenDateProperty :
    LongStorageProperty by LongStoragePropertyImpl(KEY, 0L)
