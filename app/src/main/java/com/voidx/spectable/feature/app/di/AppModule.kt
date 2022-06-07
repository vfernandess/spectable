package com.voidx.spectable.feature.app.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.voidx.spectable.firebase.FirebaseFirestoreProxy
import com.voidx.spectable.property.UserIdProperty
import org.koin.dsl.module

val appModule = module {

    factory { UserIdProperty() }

    factory<FirebaseFirestoreProxy> { FirebaseFirestoreProxy.Impl(Firebase.firestore) }
}
