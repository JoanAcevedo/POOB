package domain.stones;

import java.io.Serializable;
import domain.Board;

/**
 * Represents a Temporary stone in the Gomoku game.
 * The Temporary stone is a stone with limited permanence, and it disappears after a specified number of turns.
 * It occupies a single position on the game board and becomes permanent after a certain number of turns.
 *
 * @see Stone
 */
public class Temporary extends Stone implements Serializable{

    private int remainingTurns = 3;

    /**
     * Constructs a Temporary stone with the specified color.
     * @param color The color of the Temporary stone.
     */
    public Temporary(String color) {
        super(color);
        this.type = "Temporary";
    }
    
    /**
     * Decreases the number of remaining turns for the Temporary stone.
     * If the remaining turns reach zero, the stone is removed from the board.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     */
    public void permanenceStone(Board board, int row, int column) {
        if (remainingTurns > 0) {
            remainingTurns--;
        } else {
            board.getBox(row, column).setStone(null);
        }
    }
}
