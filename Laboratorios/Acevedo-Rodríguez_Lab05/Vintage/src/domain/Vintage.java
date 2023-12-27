package domain;

/**
 * The main class that connects all the logic of the game.
 * @author Joan Acevedo / Laura Rodríguez
 * @version 11/18/2023
 */
public class Vintage {

    private Player player;
    private Board board;

    /**
     * Constructor with dimensions specified by the player.
     * @param height Number of vertical cells in the board.
     * @param width Number of horizontal cells in the board.
     */
    public Vintage(int height, int width) {
        this.player = new Player("Gepeto");
        this.board = new Board(height, width);
    }

     /**
     * Constructor with standard dimensions (8x8) for the board.
     * @param player The player in the game.
     */
    public Vintage(Player player) {
        this.player = player;
        this.board = new Board(8, 8);
    }

    /**
     * Moves gems from one position to another based on the player's action.
     * @param position1 Initial position of the gem.
     * @param position2 Final position of the gem.
     */
    public void play(int[] position1, int[] position2) {
        board.play(position1, position2);
        this.player.setMoveNumber();
    }
    
    /**
     * Verifies alignments on the board and updates the player's score accordingly.
     */
    public void verficar(){
        int cant = board.verificar().length;
        if(cant >= 3){
            player.setScoreboard();
        }
    }

    /**
     * Returns the board where the game is taking place.
     * @return The corresponding board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the player's score.
     * @return Integer representing the player's score.
     */
    public int getScoreboardPlayer(){
        return player.getScoreboard();
    }
    
    /**
     * Returns the player's move number.
     * @return Integer representing the player's move number.
     */
    public int getMoveNumberPlayer(){
        return player.getMoveNumber();
    }

    /**
     * Resets the state of the board.
     */
    public void refresh() {
        board.reset();
    }
    
    /**
     * Gets the height of the game board.
     * @return The height of the board.
     */
    public int getHeightBoard() {
        return board.getHeight();
    }

    /**
     * Gets the width of the game board.
     * @return The width of the board.
     */
    public int getWidthBoard() {
        return board.getWidth(); 
    }
}


    
