package com.voidx.spectable.property

import com.voidx.spectable.preferences.StringStorageProperty
import com.voidx.spectable.preferences.impl.StringStoragePropertyImpl

private const val KEY = "user_id"

class UserIdProperty: StringStorageProperty by StringStoragePropertyImpl(KEY, null)
