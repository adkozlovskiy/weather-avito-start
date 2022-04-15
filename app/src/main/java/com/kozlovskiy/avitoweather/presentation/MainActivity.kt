package com.kozlovskiy.avitoweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.di.qualifier.IoDispatcher
import com.kozlovskiy.avitoweather.domain.worker.WorkerEnquirer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    @IoDispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    lateinit var workerEnquirer: WorkerEnquirer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setUpPeriodicForecast()
    }

    private fun setUpPeriodicForecast() {
        lifecycleScope.launchWhenStarted {
            withContext(ioDispatcher) {
                workerEnquirer.enquirePeriodicForecastWorker()
            }
        }
    }
}