package com.kozlovskiy.avitoweather.data.api.interceptor

import com.kozlovskiy.avitoweather.domain.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class UnitsInterceptor @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val unit = sharedPreferenceManager.getStoredUnit()
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()

        val url = originalUrl.newBuilder()
            .addQueryParameter(UNIT_PARAM_NAME, unit.serialized)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val UNIT_PARAM_NAME = "units"
    }
}