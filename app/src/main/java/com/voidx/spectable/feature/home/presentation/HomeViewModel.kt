package com.voidx.spectable.feature.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voidx.spectable.feature.home.business.HomeCommand
import com.voidx.spectable.feature.home.business.HomeEffect
import com.voidx.spectable.feature.home.business.HomeInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(
        private val interactor: HomeInteractor,
        private val effect: MutableLiveData<HomeEffect> = MutableLiveData<HomeEffect>(),
        private val disposables: CompositeDisposable = CompositeDisposable()
): ViewModel() {

    fun effect(): LiveData<HomeEffect> = effect

    fun invoke(command: HomeCommand) {
        println("invoke(command)")

       disposables += interactor
                .invoke(command)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe {
                    this.effect.postValue(it)
                }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
