package com.kozlovskiy.avitoweather.data.api.interceptor

import com.kozlovskiy.avitoweather.domain.util.LocaleUtils
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor(
    private val localeUtils: LocaleUtils,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val locale = localeUtils.getLocaleStringCode()
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(LANG_PARAM_NAME, locale)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val LANG_PARAM_NAME = "lang"
    }
}