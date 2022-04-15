package com.kozlovskiy.avitoweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.domain.worker.WorkerEnquirer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var workerEnquirer: WorkerEnquirer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Set up periodic forecast notifications.
        if (savedInstanceState == null) {
            workerEnquirer.enquirePeriodicForecastWorker()
        }
    }
}