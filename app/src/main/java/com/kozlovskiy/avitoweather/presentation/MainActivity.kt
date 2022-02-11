package com.kozlovskiy.avitoweather.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kozlovskiy.avitoweather.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}