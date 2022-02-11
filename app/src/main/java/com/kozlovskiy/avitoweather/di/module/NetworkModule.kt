package com.kozlovskiy.avitoweather.di.module

import com.google.gson.Gson
import com.kozlovskiy.avitoweather.data.api.OpenweatherService
import com.kozlovskiy.avitoweather.data.api.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(TokenInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

}