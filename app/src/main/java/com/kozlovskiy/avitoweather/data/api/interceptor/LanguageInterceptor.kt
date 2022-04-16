package com.kozlovskiy.avitoweather.data.api.interceptor

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class LanguageInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val locales = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        val primaryLocale = locales.get(0)

        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(LANG_PARAM_NAME, primaryLocale.country)
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