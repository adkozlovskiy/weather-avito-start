package com.kozlovskiy.avitoweather.domain.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.domain.usecase.GetWeatherUseCase
import com.kozlovskiy.avitoweather.domain.usecase.WeatherResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class PeriodicForecastWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val workerEnquirer: WorkerEnquirer,
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val result = when (
            val weatherResult: WeatherResult = getWeatherUseCase()
        ) {
            is WeatherResult.Success -> {
                weatherResult.location.locality?.let {
                    showNotification(
                        it,
                        weatherResult.oneCall.current.temp
                    )
                    Result.success()
                }

                Result.failure()
            }
            else -> {
                Result.failure()
            }
        }

        // Enquire this shit again
        workerEnquirer.enquirePeriodicForecastWorker()
        return result
    }

    private fun showNotification(location: String, temp: String) {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(applicationContext.getString(R.string.notification_title))
            .setContentText(
                applicationContext.getString(
                    R.string.notification_content,
                    location,
                    temp
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationId = Random().nextInt()
        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.channel_name)
            val descriptionText = applicationContext.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = applicationContext
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "periodic.worker.avito.weather"
        private const val HOUR_OF_NOTIFICATION = 9
        private const val TAG = "periodic.worker"

        fun constructRequest(): WorkRequest {
            return OneTimeWorkRequestBuilder<PeriodicForecastWorker>()
                .setInitialDelay(initialTimeDifference, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .addTag(TAG)
                .build()
        }

        private val constraints: Constraints
            get() = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        private val initialTimeDifference: Long
            get() {
                val currentDate = Calendar.getInstance()
                val dueDate = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, HOUR_OF_NOTIFICATION)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                }

                if (dueDate.before(currentDate)) {
                    dueDate.add(Calendar.HOUR_OF_DAY, 24)
                }

                return dueDate.timeInMillis - currentDate.timeInMillis
            }
    }
}