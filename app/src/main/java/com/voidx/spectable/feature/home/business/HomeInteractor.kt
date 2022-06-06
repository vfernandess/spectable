package com.voidx.spectable.feature.home.business

import com.voidx.spectable.arch.Interactor
import com.voidx.spectable.feature.home.business.mapper.UserCheckMapper
import com.voidx.spectable.feature.home.business.usecase.CheckUserSignedInUseCase
import com.voidx.spectable.feature.home.business.usecase.UpdateUserUseCase
import io.reactivex.rxjava3.core.Observable

class HomeInteractor(
        private val checkUserSignedInUseCase: CheckUserSignedInUseCase,
        private val updateUserUseCase: UpdateUserUseCase,
        private val userCheckMapper: UserCheckMapper
) : Interactor<HomeCommand, HomeEffect> {

    override fun invoke(command: HomeCommand): Observable<HomeEffect> {
        return when (command) {
            HomeCommand.ResetNavigation ->
                Observable.just(HomeEffect.Nothing)

            HomeCommand.UpdateUserID ->
                updateUserUseCase()

            else ->
                checkUserSignedInUseCase().map { userCheckMapper.map(command, it) }
        }
    }
}
