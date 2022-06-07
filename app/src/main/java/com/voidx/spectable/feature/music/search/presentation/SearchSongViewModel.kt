package com.voidx.spectable.feature.music.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voidx.spectable.feature.music.search.business.SearchSongCommand
import com.voidx.spectable.feature.music.search.business.SearchSongEffect
import com.voidx.spectable.feature.music.search.business.SearchSongInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchSongViewModel(
    private val interactor: SearchSongInteractor,
    private val effect: MutableLiveData<SearchSongEffect> = MutableLiveData<SearchSongEffect>(),
    private val disposables: CompositeDisposable = CompositeDisposable()
):ViewModel() {

    fun effect(): LiveData<SearchSongEffect> = effect

    fun invoke(command: SearchSongCommand) {
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
