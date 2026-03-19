package co.edu.uniquindio.ingesis.tenmatch.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class Card(
    val id: Int,
    val value: Int,
    val state: CardState = CardState.HIDDEN
)

enum class CardState {
    HIDDEN, VISIBLE, REMOVED
}

data class GameState(
    val playerName: String = "",
    val score: Int = 0,
    val cards: List<Card> = emptyList(),
    val isGameOver: Boolean = false,
    val selectedIndices: List<Int> = emptyList()
)

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun startGame(name: String) {
        val newBoard = GameLogic.generateBoard()
        val cards = newBoard.mapIndexed { index, value ->
            Card(id = index, value = value)
        }
        
        _uiState.update { 
            it.copy(
                playerName = name,
                score = 0,
                isGameOver = false,
                cards = cards,
                selectedIndices = emptyList()
            )
        }
    }

    fun onCardClick(index: Int) {
        val currentState = _uiState.value
        if (currentState.selectedIndices.size >= 2 || 
            currentState.cards[index].state != CardState.HIDDEN) return

        // Reveal card
        _uiState.update { state ->
            val updatedCards = state.cards.toMutableList()
            updatedCards[index] = updatedCards[index].copy(state = CardState.VISIBLE)
            state.copy(
                cards = updatedCards,
                selectedIndices = state.selectedIndices + index
            )
        }

        if (_uiState.value.selectedIndices.size == 2) {
            viewModelScope.launch {
                delay(1000) // Brief delay to show the second card
                checkMatch()
            }
        }
    }

    private fun checkMatch() {
        val currentState = _uiState.value
        if (currentState.selectedIndices.size < 2) return
        
        val idx1 = currentState.selectedIndices[0]
        val idx2 = currentState.selectedIndices[1]

        val updatedCards = currentState.cards.toMutableList()
        var newScore = currentState.score

        if (updatedCards[idx1].value + updatedCards[idx2].value == 10) {
            // Match found!
            updatedCards[idx1] = updatedCards[idx1].copy(state = CardState.REMOVED)
            updatedCards[idx2] = updatedCards[idx2].copy(state = CardState.REMOVED)
            newScore += 10
        } else {
            // No match
            updatedCards[idx1] = updatedCards[idx1].copy(state = CardState.HIDDEN)
            updatedCards[idx2] = updatedCards[idx2].copy(state = CardState.HIDDEN)
        }

        val gameIsOver = updatedCards.all { it.state == CardState.REMOVED }

        _uiState.update { 
            it.copy(
                cards = updatedCards,
                score = newScore,
                selectedIndices = emptyList(),
                isGameOver = gameIsOver
            )
        }
    }
}
