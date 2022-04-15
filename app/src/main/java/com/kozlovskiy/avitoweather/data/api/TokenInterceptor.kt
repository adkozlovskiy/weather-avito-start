package com.kozlovskiy.avitoweather.data.api

import com.kozlovskiy.avitoweather.di.qualifier.ApplicationProperties
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    @ApplicationProperties
    private val appId: String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(TOKEN_PARAM_NAME, appId)
            .addQueryParameter(EXCLUDE_PARAM_NAME, EXCLUDE)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val TOKEN_PARAM_NAME = "appid"

        const val EXCLUDE_PARAM_NAME = "exclude"
        const val EXCLUDE = "minutely,alerts"
    }
}