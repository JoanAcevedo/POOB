package domain.players;

import java.io.Serializable;

/**
 * Represents a human player in the Gomoku game.
 * Extends the Player class and provides a specific implementation for human players.
 */
public class Human extends Player implements Serializable{

     /**
     * Constructs a Human player with the specified name and color.
     *
     * @param name  The name of the human player.
     * @param color The color assigned to the human player.
     */
    public Human(String name, String color){
        super(name, color);
    }
    
    /**
     * Placeholder method for the play action of a human player.
     * This method is not implemented here and should be overridden by specific implementations.
     */
    public void play() {
    }

}
