package com.voidx.spectable.feature.app.di

import com.voidx.spectable.property.UserIdProperty
import org.koin.dsl.module


val appModule = module {

    factory { UserIdProperty() }
}
