package com.kozlovskiy.avitoweather.di.module

import com.kozlovskiy.avitoweather.di.qualifier.DefaultResolver
import com.kozlovskiy.avitoweather.domain.util.DefaultIconResolver
import com.kozlovskiy.avitoweather.domain.util.IconResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UtilsModule {

    @Binds
    @DefaultResolver
    fun bindDefaultIconResolver(impl: DefaultIconResolver): IconResolver

}