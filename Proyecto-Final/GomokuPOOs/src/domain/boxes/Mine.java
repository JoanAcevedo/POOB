package domain.boxes;

import java.io.Serializable;

import domain.*;
import domain.stones.*;

/**
 * The Mine class represents a special type of box in a game that, when activated,
 * removes stones from its neighboring boxes within a certain range on the game board.
 * It extends the Box class and implements the Serializable interface for serialization support.
 */
public class Mine extends Box implements Serializable{

    /**
     * Constructs a new Mine with the given position.
     *
     * @param position The position of the mine in the game grid.
     */
    public Mine(int[] position) {
        super(position);
        this.type = "mine";
        System.out.println("X de la poscion de una mina: " + this.position[0]);
        System.out.println("Y de la poscion de una mina: " + this.position[1]);
        System.out.println("-------------------------------------------------");
    }

    /**
     * Performs the action associated with the mine, which involves removing stones
     * from its neighboring boxes within a certain range on the game board.
     *
     * @param board The game board on which the action is performed.
     */
    public void action(Board board, int row, int column) {
        int currentRow = row;
        int currentColumn = column;
        for (int i = currentRow - 1; i <= currentRow + 1; i++) {
            for (int j = currentColumn - 1; j <= currentColumn + 1; j++) {
                if (isValidCoordinate(board, i, j)) {
                    Stone stoneToRemove = board.getStoneAt(i, j);
                    if (stoneToRemove != null) {
                        board.getBox(i, j).setStone(null);
                        this.stone = null;
                    }   
                }
            }
        }

        board.removeStoneAt(currentRow, currentColumn);
    }

    /**
     * Checks if the specified coordinates are within the bounds of the game board.
     *
     * @param board  The game board.
     * @param row    The row coordinate.
     * @param column The column coordinate.
     * @return True if the coordinates are valid; false otherwise.
     */
    private boolean isValidCoordinate(Board board, int row, int column) {
        return row >= 0 && row < board.getHeight() && column >= 0 && column < board.getWidth();
    }
}


