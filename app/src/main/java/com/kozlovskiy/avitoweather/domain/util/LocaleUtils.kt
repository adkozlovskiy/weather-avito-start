package com.kozlovskiy.avitoweather.domain.util

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import javax.inject.Inject

class LocaleUtils @Inject constructor() {
    /**
     * Return locale code
     * Samples: ru, en, fr
     */
    fun getLocaleStringCode(): String {
        return ConfigurationCompat
            .getLocales(Resources.getSystem().configuration)
            .get(0)
            .country
            .lowercase()
    }
}