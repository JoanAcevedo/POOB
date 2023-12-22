package domain.stones;

import java.io.Serializable;
import domain.Board;

/**
 * Represents a Normal stone in the Gomoku game.
 * The Normal stone has the ability to check for adjacent stones and determine its permanence based on the count.
 * It occupies a single position on the game board and becomes permanent if it has at least five adjacent stones.
 *
 * @see Stone
 */
public class NormalStone extends Stone implements Serializable{

    private Stone stone;
    /**
     * Constructs a Normal stone with the specified color.
     *
     * @param color The color of the Normal stone.
     */
    public NormalStone(String color) {
        super(color);
        this.type = "Normal";
    }

    /**
     * Checks for adjacent stones and sets the permanence of the Normal stone based on the count.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     */
    public void permanenceStone(Board board, int row, int column) {
        int adjacentStones = 0;
        if (board != null) {
            adjacentStones = stone.countAdjacentStones(row, column);
        }

        if (adjacentStones >= 5) {
            permanence = true;
        } else {
            permanence = false;
        }
    }

    /**
     * Sets the position for the Normal stone on the board, representing its single position.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     */
    public void space(Board board, int row, int column) {
        if (!permanence && stone.isValidMove(row, column)) {
            int[] originalPosition = {row, column};
            this.position = originalPosition;
        }
    }
}
