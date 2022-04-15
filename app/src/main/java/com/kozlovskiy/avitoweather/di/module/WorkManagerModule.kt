package com.kozlovskiy.avitoweather.di.module

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    fun provideWorkManager(
        @ApplicationContext appContext: Context,
    ): WorkManager {
        return WorkManager.getInstance(appContext)
    }
}