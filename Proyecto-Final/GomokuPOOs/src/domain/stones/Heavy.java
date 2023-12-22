package domain.stones;

import java.io.Serializable;

import domain.Board;

/**
 * Represents a Heavy stone in the Gomoku game.
 * The Heavy stone has the ability to check for adjacent stones and determine its permanence based on the count.
 * Additionally, it can occupy multiple positions on the game board, representing its heavy nature.
 * The Heavy stone is not permanent unless it has at least five adjacent stones.
 *
 * @see Stone
 */
public class Heavy extends Stone implements Serializable{

    private int[] position1;
    private int[] position2;
    private int[] position3;

    /**
     * Constructs a Heavy stone with the specified color.
     *
     * @param color The color of the Heavy stone.
     */
    public Heavy(String color) {
        super(color);
        this.type = "Heavy";
    }

    /**
     * Checks for adjacent stones and sets the permanence of the Heavy stone based on the count.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     */
    public void permanenceStone(Board board, int row, int column) {
        int adjacentStones = countAdjacentStones(row, column);
        if (adjacentStones >= 5) {
            permanence = true; // finaliza
            if (position1 != null) board.getBox(position1[0], position1[1]).setStone(this);
            if (position2 != null) board.getBox(position2[0], position2[1]).setStone(this);
            if (position3 != null) board.getBox(position3[0], position3[1]).setStone(this);
        } else {
            permanence = false; // puede seguir jugando
        }
    }

    /**
     * Sets the additional positions for the Heavy stone on the board, representing its heavy nature.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     */
    public void space(Board board, int row, int column) {
        if (!permanence && isValidHeavyMove(board, row, column)) {
            int[][] positions = {
                {row, column},
                {row + 1, column},
                {row, column + 1},
                {row + 1, column + 1},
            };
            boolean isValidPosition = true;
            for (int[] pos : positions) {
                if (!board.isValidMove(pos[0], pos[1])) {
                    isValidPosition = false;
                    break;
                }
            }
            if (isValidPosition) {
                for (int[] pos : positions) {
                    board.getBox(pos[0], pos[1]).setStone(this);
                }
            }
        }
    }

    /**
     * Checks if a move for a heavy stone is valid on the board.
     * A valid move for a heavy stone requires that all four corners of the intended position are valid.
     *
     * @param board The game board.
     * @param row The row index on the board.
     * @param column The column index on the board.
     * @return True if the move is valid for a heavy stone, false otherwise.
     */
    private boolean isValidHeavyMove(Board board, int row, int column) {
        return board.isValidMove(row, column) &&
            board.isValidMove(row + 1, column) &&
            board.isValidMove(row, column + 1) &&
            board.isValidMove(row + 1, column + 1);
    }

    /**
     * Checks if the move for the Heavy stone is valid on the board.
     *
     * @param board  The game board on which the stone is placed.
     * @param row    The row index of the stone on the board.
     * @param column The column index of the stone on the board.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValidMove(int row, int column) {
        return super.isValidMove(row, column) &&
               isValidMove(position1) &&
               isValidMove(position2) &&
               isValidMove(position3);
    }

    /**
     * Checks if a move to the specified position is valid on the board.
     * A move is considered valid if the provided position is null or the corresponding board position is valid.
     *
     * @param position An array containing the row and column indices of the intended move.
     * @return True if the move is valid, false otherwise.
     */
    private boolean isValidMove(int[] position) {
        return position == null || board.isValidMove(position[0], position[1]);
    }
}
