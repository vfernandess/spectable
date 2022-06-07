package com.voidx.spectable.feature.music.space.di

import com.voidx.spectable.feature.music.space.business.MusicSpaceInteractor
import com.voidx.spectable.feature.music.space.business.usecase.GetMySongsUseCase
import com.voidx.spectable.feature.music.space.presentation.MusicSpaceViewModel
import com.voidx.spectable.feature.music.space.view.MusicSpaceFragment
import com.voidx.spectable.feature.music.space.view.MusicSpaceRouter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val musicSpaceModule = module {

    scope<MusicSpaceFragment> {

        factory<GetMySongsUseCase> { GetMySongsUseCase.Impl(get(), get()) }

        factory { MusicSpaceInteractor(get()) }

        scoped<MusicSpaceRouter> { navigation ->
            MusicSpaceRouter.Impl(navigation.get())
        }

        viewModel { MusicSpaceViewModel(get()) }
    }
}
