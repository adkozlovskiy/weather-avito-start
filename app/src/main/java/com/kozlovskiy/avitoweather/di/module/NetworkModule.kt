package com.kozlovskiy.avitoweather.di.module

import android.content.Context
import com.google.gson.Gson
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.data.api.OpenweatherService
import com.kozlovskiy.avitoweather.data.api.interceptor.LanguageInterceptor
import com.kozlovskiy.avitoweather.data.api.interceptor.TokenInterceptor
import com.kozlovskiy.avitoweather.data.api.interceptor.UnitsInterceptor
import com.kozlovskiy.avitoweather.di.qualifier.ApplicationProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOpenweatherService(
        okHttpClient: OkHttpClient,
        gson: Gson,
    ): OpenweatherService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenInterceptor: TokenInterceptor,
        unitsInterceptor: UnitsInterceptor,
        languageInterceptor: LanguageInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(unitsInterceptor)
            .addInterceptor(languageInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    @ApplicationProperties
    fun provideAppId(
        @ApplicationContext
        appContext: Context,
    ): String {
        return appContext.getString(R.string.appid)
    }
}