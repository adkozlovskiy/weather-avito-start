package com.kozlovskiy.avitoweather.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

inline fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State,
    crossinline action: suspend (value: T) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    lifecycleOwner.repeatOnLifecycle(state) {
        collect { action(it) }
    }
}