package com.voidx.spectable.feature.music.space.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.voidx.spectable.feature.music.space.business.MusicSpaceCommand
import com.voidx.spectable.feature.music.space.business.MusicSpaceEffect
import com.voidx.spectable.feature.music.space.business.MusicSpaceInteractor
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.schedulers.Schedulers

class MusicSpaceViewModel(
    private val interactor: MusicSpaceInteractor,
    private val effect: MutableLiveData<MusicSpaceEffect> = MutableLiveData(),
    private val disposables: CompositeDisposable = CompositeDisposable()
) : ViewModel() {

    fun effect(): LiveData<MusicSpaceEffect> = effect

    fun invoke(command: MusicSpaceCommand) {
        disposables += interactor
            .invoke(command)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .subscribe {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    this.effect.value = it
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
