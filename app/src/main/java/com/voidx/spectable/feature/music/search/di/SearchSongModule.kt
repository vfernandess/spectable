package com.voidx.spectable.feature.music.search.di

import com.voidx.spectable.BuildConfig
import com.voidx.spectable.feature.music.search.business.SearchSongInteractor
import com.voidx.spectable.feature.music.search.business.mapper.SongMapper
import com.voidx.spectable.feature.music.search.business.usecase.AddSongUseCase
import com.voidx.spectable.feature.music.search.business.usecase.RefreshTokenUseCase
import com.voidx.spectable.feature.music.search.business.usecase.ResetExpirationDateUseCase
import com.voidx.spectable.feature.music.search.business.usecase.SearchSongUseCase
import com.voidx.spectable.feature.music.search.infra.networking.SpotifyAPI
import com.voidx.spectable.feature.music.search.infra.repository.AccessTokenRepository
import com.voidx.spectable.feature.music.search.infra.repository.SearchSongRepository
import com.voidx.spectable.feature.music.search.presentation.SearchSongViewModel
import com.voidx.spectable.feature.music.search.view.SearchSongFragment
import com.voidx.spectable.feature.music.search.view.SearchSongRouter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val searchSongModule = module {

    scope<SearchSongFragment> {

        scoped<SpotifyAPI> {
            val loggingInterceptor = HttpLoggingInterceptor()
            val level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BASIC
            else
                HttpLoggingInterceptor.Level.NONE

            loggingInterceptor.setLevel(level)

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.spotify.com/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()

            return@scoped retrofit.create(SpotifyAPI::class.java)
        }

        scoped<RefreshTokenUseCase> { RefreshTokenUseCase.Impl(get(), get()) }

        factory<SongMapper> { SongMapper.Impl() }

        scoped<SearchSongUseCase> { SearchSongUseCase.Impl(get(), get()) }

        scoped<AddSongUseCase> { AddSongUseCase.Impl(get(), get()) }

        scoped<ResetExpirationDateUseCase> { ResetExpirationDateUseCase.Impl(get()) }

        factory<SearchSongRepository> { SearchSongRepository.Impl(get(), get()) }

        scoped<SearchSongRouter> { navigation ->
            SearchSongRouter.Impl(navigation.get())
        }

        scoped<AccessTokenRepository> {
            val local = AccessTokenRepository.LocalAccessTokenRepository(get(), get())
            val remote = AccessTokenRepository.RemoteAccessTokenRepository(get())
            return@scoped AccessTokenRepository.Impl(remote, local)
        }

        scoped { SearchSongInteractor(get(), get(), get(), get()) }

        viewModel { SearchSongViewModel(get()) }
    }

}
