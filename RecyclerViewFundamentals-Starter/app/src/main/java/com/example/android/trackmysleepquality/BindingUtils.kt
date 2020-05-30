package com.example.android.trackmysleepquality

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.trackmysleepquality.database.SleepNight

/**
 * Functions in Kotlin without class keyword or a top level declaration would be static
 */
@BindingAdapter("sleepDurationText")
fun TextView.setSleepDurationString(sleepNight: SleepNight?) {
    sleepNight?.let {
        text = convertDurationToFormatted(sleepNight.startTimeMilli,
                sleepNight.endTimeMilli, context.resources)
    }
}

@BindingAdapter("sleepQualityText")
fun TextView.setSleepQualityString(sleepNight: SleepNight?) {
    sleepNight?.let {
        text = convertNumericQualityToString(sleepNight.sleepQuality, context.resources)
    }
}

@BindingAdapter("sleepQualityImage")
fun ImageView.setQualityImage(sleepNight: SleepNight?) {
    sleepNight?.let {
        setImageResource(when (sleepNight.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }
}