package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(var finalScore: Int): ViewModel() {

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private var _playAgain = MutableLiveData<Boolean>()
    val playAgain: LiveData<Boolean>
        get() = _playAgain

    init {
        Timber.i("ScoreViewModel created")
        _score.value = finalScore
        _playAgain.value = false
    }

    fun onPlayAgain() { _playAgain.value = true }
    fun onPlayAgainFinished() { _playAgain.value = false }
}