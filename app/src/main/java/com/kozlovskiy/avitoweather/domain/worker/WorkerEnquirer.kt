package com.kozlovskiy.avitoweather.domain.worker

import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WorkerEnquirer @Inject constructor(
    private val workManager: WorkManager,
) {
    fun enquirePeriodicForecastWorker() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest: WorkRequest = PeriodicWorkRequestBuilder<PeriodicForecastWorker>(
            PERIOD_HOURS, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueue(workRequest)
    }

    companion object {
        const val PERIOD_HOURS = 12L
    }
}