package com.voidx.spectable.feature.home.di

import com.google.firebase.auth.FirebaseAuth
import com.voidx.spectable.feature.home.business.HomeInteractor
import com.voidx.spectable.feature.home.business.mapper.UserCheckMapper
import com.voidx.spectable.feature.home.business.usecase.CheckUserSignedInUseCase
import com.voidx.spectable.feature.home.business.usecase.UpdateUserUseCase
import com.voidx.spectable.feature.home.presentation.HomeViewModel
import com.voidx.spectable.feature.home.view.HomeFragment
import com.voidx.spectable.feature.home.view.HomeRouter
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {

    scope<HomeFragment> {

        factory<UserCheckMapper> { UserCheckMapper.Impl() }

        factory<CheckUserSignedInUseCase> { CheckUserSignedInUseCase.Impl(get()) }

        factory<UpdateUserUseCase> {
            UpdateUserUseCase.Impl(
                    FirebaseAuth.getInstance(),
                    get()
            )
        }

        scoped<HomeRouter> { navigation ->
            HomeRouter.Impl(navigation.get())
        }

        scoped { HomeInteractor(get(), get(), get()) }

        viewModel { HomeViewModel(get()) }
    }

}
