package domain.stones;

import java.io.Serializable;
import domain.Board;

/**
 * Represents a stone used in the Gomoku game.
 * Stones can be of different colors and types.
 */
public class Stone implements Serializable{

    public String color;
    protected int[] position;
    public boolean estate;
    public boolean permanence;
    protected Board board;
    protected String type;
    private int peso; 

    /**
     * Constructs a Stone object with the specified color and position.
     *
     * @param color The color of the stone.
     * @param position The position of the stone on the board.
     */
    public Stone(String color, int[] position) {
        this.color = color;
        this.position = position;
        this.estate = false;  
    }

    /**
     * Checks if the stone is heavy.
     *
     * @return True if the stone is heavy, false otherwise.
     */
    public boolean isHeavy() {
        return this.peso == 2; 
    }

    /**
     * Constructs a Stone object with the specified color.
     *
     * @param color The color of the stone.
     */
    public Stone(String color){
        this.color = color;
    }

    /**
     * Placeholder method for permanence of a stone on the board.
     *
     * @param board The game board.
     * @param fila The row index on the board.
     * @param columna The column index on the board.
     */
    public void permanenceStone(Board board, int fila, int columna) {
    }

    /**
     * Placeholder method for handling empty space on the board.
     *
     * @param board The game board.
     * @param fila The row index on the board.
     * @param columna The column index on the board.
     */
    public void space(Board board, int fila, int columna) {

    }
    
    /**
     * Counts the number of adjacent stones to the specified position on the board.
     *
     * @param fila The row index on the board.
     * @param columna The column index on the board.
     * @return The count of adjacent stones.
     */
    public int countAdjacentStones(int fila, int columna) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    int adjacentFila = fila + i;
                    int adjacentColumna = columna + j;
                    if (board.isValidMove(adjacentFila, adjacentColumna)) {
                        Stone adjacentStone = board.getStoneAt(adjacentFila, adjacentColumna);
                        if (adjacentStone != null && adjacentStone.getColor().equals(board.getStoneAt(fila, columna).getColor())) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    /**
     * Checks if a move to the specified position is valid.
     *
     * @param row The row index on the board.
     * @param column The column index on the board.
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(int row, int column) {
        board.isValidMove(row,column);
        return true;
    }
    
    /**
     * Removes the stone at the specified position on the board.
     *
     * @param row The row index on the board.
     * @param column The column index on the board.
     */
    public void removeStoneAt(int row, int column) {
        board.removeStoneAt(row,column);
    }
    
    /**
     * Retrieves the position of the stone on the board.
     *
     * @return An array containing the row and column indices of the stone.
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Retrieves the permanence state of the stone on the board.
     *
     * @return True if the stone is permanent, false otherwise.
     */
    public boolean getState() {
        return permanence;
    }

    /**
     * Retrieves the color of the stone.
     *
     * @return The color of the stone.
     */
    public String getColor() {
        return color;
    }

    /**
     * Retrieves the type of the stone.
     *
     * @return The type of the stone.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Sets the position of the stone on the board.
     *
     * @param newPosition An array containing the new row and column indices of the stone.
     */
    public void setPosition(int[] newPosition){
        this.position = newPosition;
    }
    
    /**
     * Sets the game board for the stone.
     *
     * @param board The game board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }
}
