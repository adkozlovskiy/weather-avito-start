package com.kozlovskiy.avitoweather.di.module

import android.content.Context
import android.content.SharedPreferences
import com.kozlovskiy.avitoweather.domain.SharedPreferenceManager
import com.kozlovskiy.avitoweather.domain.SharedPreferenceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(
    includes = [
        SharedPreferenceBinds::class
    ]
)
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext appContext: Context,
    ): SharedPreferences {
        return appContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private const val SHARED_PREFERENCES_NAME = "app.shared.preference"
}

@Module
@InstallIn(SingletonComponent::class)
interface SharedPreferenceBinds {

    @Binds
    fun bindSharedPreferenceManager(impl: SharedPreferenceManagerImpl): SharedPreferenceManager

}