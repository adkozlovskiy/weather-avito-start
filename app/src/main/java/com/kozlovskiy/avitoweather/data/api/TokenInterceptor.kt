package com.kozlovskiy.avitoweather.data.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(TOKEN_PARAM_NAME, TOKEN)
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

        // todo can we hide this?
        const val TOKEN = "ccd11b37c3e195a9d65eef947fccef27"
        const val EXCLUDE = "minutely,alerts"
    }
}