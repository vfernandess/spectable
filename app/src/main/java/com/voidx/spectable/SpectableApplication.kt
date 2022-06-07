package com.voidx.spectable

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voidx.spectable.feature.app.di.appModule
import com.voidx.spectable.feature.home.di.homeModule
import com.voidx.spectable.feature.music.space.di.musicSpaceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpectableApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()

        startKoin {
            androidContext(this@SpectableApplication)
            modules(listOf(appModule, homeModule, musicSpaceModule))
        }
    }
}
