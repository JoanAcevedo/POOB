package domain;

import java.io.Serializable;
import java.util.Random;

import domain.boxes.Box;
import domain.boxes.Golden;
import domain.boxes.Mine;
import domain.boxes.NormalBox;
import domain.boxes.Teleport;
import domain.stones.Heavy;
import domain.stones.Stone;

public class Board implements Serializable{

    private int height;
    private int width;
    private Box[][] boxes;
    private int numberOfMoves;
    private GomokuPOOs game;
    private int[] lastHeavy;

    /**
     * Initializes a new `Board` object with the specified dimensions and populates it with empty `NormalBox` objects.
     *
     * @param height The number of rows on the board.
     * @param width The number of columns on the board.
     *
     * @return A newly created `Board` object with the specified dimensions and empty cells.
     */
    public Board(GomokuPOOs game, int height, int width) {
        this.game = game;
        this.height = height;
        this.width = width;
        this.boxes = new Box[height][width];
        this.numberOfMoves = 0;
        creatorBoxes(height, width);
    }

    /**
     * Checks if the cell at the specified position is empty.
     *
     * @param row The row number of the cell to be checked (integer).
     * @param column The column number of the cell to be checked (integer).
     *
     * @return True if the cell is empty, false otherwise.
     */
    public void play(Stone stone, int row, int column) {
        if (isCellEmpty(row, column)) {
            boxes[row][column].setStone(stone);
            stone.setBoard(this);
            if(stone.getType().equals("Heavy")){ // Accion de la p. heavy
                stone.space(this, row, column);
                
            }
            
            boxes[row][column].action(this, row, column); // Accion de cualquier casilla
            checkForWinner(stone, row, column);
            isThereAWinner(stone, row, column);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    Stone stoneT = boxes[i][j].getStone();
                    if (stoneT != null) {
                        if (stoneT.getType().equals("Temporary")) {
                            stoneT.permanenceStone(this, i, j); // Accion de la p. temporary
                        }
                    }
                }
            }
            numberOfMoves++;
        }
    }
    
    /**
     * Checks if the board is full and there is no winner with five consecutive stones.
     *
     * @return True if the board is full and there is no winner, false otherwise.
     */
    public boolean isGameDraw() {
        if (getNumberOfMoves() == getHeight() * getWidth()) {
            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    Stone stone = getStoneAt(i, j);
                    if (stone != null && !isThereAWinner(stone, i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }


    /**
     * Checks if the cell at the specified position is empty.
     *
     * @param row The row number of the cell to be checked (integer).
     * @param column The column number of the cell to be checked (integer).
     *
     * @return True if the cell is empty, false otherwise.
     */
    public boolean isCellEmpty(int row, int column) {
        if (row < 0 || row >= height || column < 0 || column >= width) {
            return false;
        }
        return boxes[row][column].getStone() == null;
    }

    /**
     * Checks if the provided stone placed at the specified position results in a winner.
     *
     * @param stone The stone object placed on the board.
     * @param row The row where the stone was placed.
     * @param column The column where the stone was placed.
     *
     * @return Nothing. Prints a message announcing the winner if there is one.
     */
    public String checkForWinner(Stone stone, int row, int column) {
        String colorVal = "";
        String color = stone.getColor();
        if (countConsecutiveStones(color, row, column, 0, 1, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, 0, -1, getHeight(), getWidth()) >= 6) {
            System.out.println(color + " ha ganado horizontalmente!");
            colorVal = color;
        }
        if (countConsecutiveStones(color, row, column, 1, 0, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, -1, 0, getHeight(), getWidth()) >= 6) {
            System.out.println(color + " ha ganado verticalmente!");
            colorVal = color;
        }
        if (countConsecutiveStones(color, row, column, -1, -1, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, 1, 1, getHeight(), getWidth()) >= 6) {
            System.out.println(color + " ha ganado diagonalmente!");
            colorVal = color;
        }
        if (countConsecutiveStones(color, row, column, -1, 1, getHeight(),getWidth()) + countConsecutiveStones(color, row, column, 1, -1, getHeight(), getWidth()) >= 6) {
            System.out.println(color + " ha ganado diagonalmente!");
            colorVal = color;
        }
        return colorVal;
    }

    /**
     * Removes the stone object from the specified position.
     *
     * @param row The row index of the position (integer).
     * @param column The column index of the position (integer).
     */
    public void removeStoneAt(int row, int column) {
        boxes[row][column].setStone(null);
    }
    
    /**
     * Checks if the last move resulted in a winner
     * @param stone The stone that was just played.
     * @param row The row where the stone was played.
     * @param column The column where the stone was played.
     * @param board The current state of the board.
     * @return true if the last move resulted in a winner, false otherwise.
     */
    public boolean isThereAWinner(Stone stone, int row, int column) {
        String color = stone.getColor();
        if (countConsecutiveStones(color, row, column, 0, 1, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, 0, -1, getHeight(), getWidth()) >= 6) {
            return true;
        }
        if (countConsecutiveStones(color, row, column, 1, 0, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, -1, 0, getHeight(), getWidth()) >= 6) {
            return true;
        }
        if (countConsecutiveStones(color, row, column, -1, -1, getHeight(), getWidth()) + countConsecutiveStones(color, row, column, 1, 1, getHeight(), getWidth()) >= 6) {
            return true;
        }
        return countConsecutiveStones(color, row, column, -1, 1, getHeight(),getWidth()) + countConsecutiveStones(color, row, column, 1, -1, getHeight(), getWidth()) >= 6;
    }


    /**
     * Checks if a move is valid by verifying if the specified position is within the board boundaries and is unoccupied.
     *
     * @param row The row index of the move (integer).
     * @param column The column index of the move (integer).
     * @return True if the move is valid, false otherwise.
     */
    public boolean isValidMove(int row, int column) {
        return row >= 0 && row < boxes.length && column >= 0 && column < boxes[0].length;
        // return row >= 0 && row < boxes.length && column >= 0 && column < boxes[0].length && boxes[row][column] == null;
    }
    
    /**
     * Resets the board
     */
    public void reset() {
        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width ; j++){
                boxes[i][j] = null;
                int[] value = {i, j};
                boxes[i][j] = new NormalBox(value);
            }
        }
    }
    
    /**
     * Retrieves the GomokuPOOs object representing the current game state.
     *
     * @return The GomokuPOOs object representing the current game state.
     */
    public GomokuPOOs getGomokuPOOs(){
        return this.game;
    }

    /**
     * Retrieves the total number of moves played in the game.
     *
     * @return The total number of moves played (integer).
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * Retrieves the number of rows on the board.
     *
     * @return The number of rows (integer).
     */
    public int getRow() {
        return height;
    }

    /**
     * Retrieves the number of columns on the board.
     *
     * @return The number of columns (integer).
     */
    public int getColumn() {
        return width;
    }

    /**
     * Gets the box object at the specified position.
     *
     * @param row The row index of the box (integer).
     * @param column The column index of the box (integer).
     * @return The box object at the specified position.
     */
    public Box getBox(int row, int column){
        return boxes[row][column];
    }
    
    /**
     * Gets the height of the board.
     *
     * @return The height of the board (integer).
     */
    public int getHeight(){
        return this.height;
    }

    /**
     * Gets the width of the board.
     *
     * @return The width of the board (integer).
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Gets the stone object at the specified position.
     *
     * @param row The row index of the position (integer).
     * @param column The column index of the position (integer).
     * @return The stone object at the specified position, or null if no stone exists in that position.
     */
    public Stone getStoneAt(int row, int column) {
        return boxes[row][column].getStone();
    }

    /**
     * Retrieves the position of the last heavy stone played on the Gomoku board.
     *
     * @return An array of integers representing the row index and column index of the last heavy stone played.
     */
    public int[] getLastHeavy() {
        return this.lastHeavy;
    }
    
    /**
     * Sets the position of the last heavy stone played on the Gomoku board.
     *
     * @param i The row index of the last heavy stone played.
     * @param j The column index of the last heavy stone played.
     */
    public void setLastHeavy(int i, int j) {
        this.lastHeavy = new int[]{i, j}; 
    }
    

    /**
     * Creates and populates a grid of boxes with the specified height and width.
     * This method generates a grid of boxes with random placement of special boxes (Mines, Teleports, and Goldens)
     * based on a specified probability. The total number of special boxes is limited to a maximum count.
     *
     * @param height The number of rows in the grid.
     * @param width  The number of columns in the grid.
     */
    private void creatorBoxes(int height, int width){
        Random random = new Random();
        int specialBoxesCount = 0;
        int maxSpecialBoxes = 15; // < ----------
        double specialProbability = 0.2; // Probabilidad de una casilla especial
        
        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width ; j++){
                int[] valor = {i, j};
                if (specialBoxesCount < maxSpecialBoxes && random.nextDouble() < specialProbability) {
                    double specialType = random.nextDouble();
                    if (specialType < 0.33) { // Mina (33% de probabilidad)
                        boxes[i][j] = new Mine(valor);
                        specialBoxesCount++;
                    } else if (specialType < 0.66) { // Teleport (33% de probabilidad)
                        boxes[i][j] = new Teleport(valor, this);
                        specialBoxesCount++;
                    } else { // Golden (34% de probabilidad)
                        boxes[i][j] = new Golden(valor);
                        specialBoxesCount++;
                    }
                } else {
                    boxes[i][j] = new NormalBox(valor);
                }
            }
        }
    }

    /**
     * Counts the number of consecutive stones of the specified color in a given direction starting from a specific position.
     *
     * @param color The color of the stones to count.
     * @param row The starting row.
     * @param column The starting column.
     * @param rowIncrement The row increment for each iteration.
     * @param columnIncrement The column increment for each iteration.
     * @param height The height of the board.
     * @param width The width of the board.
     *
     * @return The number of consecutive stones.
     */
    private int countConsecutiveStones(String color, int row, int column, int rowIncrement, int columnIncrement, int height, int width) {
        int count = 0;
        while (row >= 0 && row < height && column >= 0 && column < width) {
            Stone currentStone = getStoneAt(row, column);
            if (currentStone != null && currentStone.getColor().equals(color)) {
                count++;
            } else {
                break;
            }
            row += rowIncrement;
            column += columnIncrement;
        }
        return count;
    }
}
