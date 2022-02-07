package com.kozlovskiy.avitoweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kozlovskiy.avitoweather.R
import com.kozlovskiy.avitoweather.presentation.summary.SummaryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SummaryFragment.newInstance())
                .commitNow()
        }
    }
}