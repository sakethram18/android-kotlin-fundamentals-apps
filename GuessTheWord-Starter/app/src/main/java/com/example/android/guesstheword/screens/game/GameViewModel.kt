package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    // The current word
    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word
    val wordHint: LiveData<WordHint> = Transformations.map(word) { word ->
        val random = (1..word.length).random()
        WordHint(word.length, random, word[random - 1].toUpperCase())
    }

    data class WordHint(val wordSize: Int, val randomPosition: Int, val randomLetter: Char)

    // The current score
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Game finished event
    private var _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    private var _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long>
        get() = _currentTime
    val currentTimeString: LiveData<String> = Transformations.map(currentTime) {
        time -> DateUtils.formatElapsedTime(time)
    }

    private var timer: CountDownTimer

    init {
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()
        _gameFinished.value = false

        timer = object : CountDownTimer(GAME_DURATION, ONE_SECOND) {
            override fun onFinish() {
                _currentTime.value = GAME_FINISH
                onGameFinished()
            }

            override fun onTick(p0: Long) {
                _currentTime.value = p0/ ONE_SECOND
            }
        }
        timer.start()
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }


    /** Methods for buttons presses **/
    fun onSkip() {
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishCompleted() {
        _gameFinished.value = false
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        if (wordList.isNotEmpty()) {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        } else {
            resetList()
        }
    }

    fun onGameFinished() {
        _gameFinished.value = true
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

    companion object {
        private const val GAME_FINISH = 0L
        private const val ONE_SECOND = 1000L
        private const val GAME_DURATION = 30000L
    }
}