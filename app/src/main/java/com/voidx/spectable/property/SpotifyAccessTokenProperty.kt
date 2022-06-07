package com.voidx.spectable.property

import com.voidx.spectable.preferences.StringStorageProperty
import com.voidx.spectable.preferences.impl.StringStoragePropertyImpl

private const val KEY = "access-token"

class SpotifyAccessTokenProperty :
    StringStorageProperty by StringStoragePropertyImpl(KEY, null)
