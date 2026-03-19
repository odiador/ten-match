package co.edu.uniquindio.ingesis.tenmatch.game

import kotlin.random.Random

/**
 * Logic for the Ten Match game.
 */
object GameLogic {

    /**
     * Generates a 4x4 matrix (16 cards) where every card has at least one partner
     * that sums to 10.
     */
    fun generateBoard(): List<Int> {
        val board = MutableList(16) { 0 }
        val indices = (0..15).toMutableList()
        indices.shuffle()

        // Create 8 pairs that sum to 10
        for (i in 0 until 8) {
            val idx1 = indices.removeAt(0)
            val idx2 = indices.removeAt(0)
            
            val n = Random.nextInt(1, 10) // 1 to 9
            board[idx1] = n
            board[idx2] = 10 - n
        }

        return board
    }
}
