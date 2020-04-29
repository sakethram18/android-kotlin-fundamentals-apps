package com.example.android.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(var finalScore: Int): ViewModel() {
    var score = finalScore
    init {
        Timber.i("ScoreViewModel created")
    }
}