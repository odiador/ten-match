package com.example.tenmatch.game

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Card(
    val id: Int,
    val value: Int,
    val state: CardState = CardState.HIDDEN
)

enum class CardState {
    HIDDEN, VISIBLE, REMOVED
}

class GameViewModel : ViewModel() {
    private val _cards = mutableStateListOf<Card>()
    val cards: List<Card> get() = _cards

    private val _selectedIndices = mutableStateListOf<Int>()
    
    val playerName = mutableStateOf("")
    val score = mutableStateOf(0)
    val isGameOver = mutableStateOf(false)

    fun startGame(name: String) {
        playerName.value = name
        score.value = 0
        isGameOver.value = false
        val newBoard = GameLogic.generateBoard()
        _cards.clear()
        newBoard.forEachIndexed { index, value ->
            _cards.add(Card(id = index, value = value))
        }
    }

    fun onCardClick(index: Int) {
        if (_selectedIndices.size >= 2 || _cards[index].state != CardState.HIDDEN) return

        // Reveal card
        _cards[index] = _cards[index].copy(state = CardState.VISIBLE)
        _selectedIndices.add(index)

        if (_selectedIndices.size == 2) {
            viewModelScope.launch {
                delay(1000) // Brief delay to show the second card
                checkMatch()
            }
        }
    }

    private fun checkMatch() {
        val idx1 = _selectedIndices[0]
        val idx2 = _selectedIndices[1]

        if (_cards[idx1].value + _cards[idx2].value == 10) {
            // Match found!
            _cards[idx1] = _cards[idx1].copy(state = CardState.REMOVED)
            _cards[idx2] = _cards[idx2].copy(state = CardState.REMOVED)
            score.value += 10
        } else {
            // No match
            _cards[idx1] = _cards[idx1].copy(state = CardState.HIDDEN)
            _cards[idx2] = _cards[idx2].copy(state = CardState.HIDDEN)
        }

        _selectedIndices.clear()

        // Check if game is over
        if (_cards.all { it.state == CardState.REMOVED }) {
            isGameOver.value = true
        }
    }
}
