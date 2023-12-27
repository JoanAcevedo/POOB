package domain;

/**
 * Player who is responsible for making gem movements.
 * @author Joan Acevedo / Laura Rodriguez
 * @version 18/11/2023
 */
public class Player {
    private final String NAME;
    private static int scoreboard;
    private boolean turn;
    private static int moveNumber;  
    /**
     * Constructs a player with a specific name.
     * @param name The name of the player.
     */
    public Player(String name) {
        this.NAME = name;
        this.scoreboard = 0;
        this.turn = false;
    }

    /**
     * Gets the name of the player.
     * @return The name of the player.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Gets the scoreboard of the player.
     * @return The scoreboard of the player.
     */
     public static int getScoreboard() {
        return scoreboard;
    }
    
    /**
     * Gets the move number of the player.
     * @return The move number of the player.
     */
    public static int getMoveNumber() {
        return moveNumber;
    }
    
    /**
     * Increases the move number by one.
     */
    public void setMoveNumber() {
        this.moveNumber ++;
    }
    
    /**
     * Increases the player's scoreboard by 10 points.
     */
    public void setScoreboard() {
        this.scoreboard += 10;
    }
}
