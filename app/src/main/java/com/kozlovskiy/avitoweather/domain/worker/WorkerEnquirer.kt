package com.kozlovskiy.avitoweather.domain.worker

import androidx.work.WorkManager
import androidx.work.WorkRequest
import javax.inject.Inject

class WorkerEnquirer @Inject constructor(
    private val workManager: WorkManager,
) {
    fun enquirePeriodicForecastWorker() {
        val workRequest: WorkRequest = PeriodicForecastWorker.constructRequest()
        workManager.enqueue(workRequest)
    }
}