package com.voidx.spectable.arch

import io.reactivex.rxjava3.core.Observable

typealias Interactor<Command, Effect> = (command: Command) -> Observable<Effect>
