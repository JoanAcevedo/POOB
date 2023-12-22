package domain.boxes;

import domain.stones.Stone;

import java.io.Serializable;

import domain.*;

/**
 * The Box class represents a box in a game containing a stone. It provides methods
 * to manipulate and retrieve information about the stone in the box.
 */
public class Box implements Serializable{

    protected int[] position;
    protected String type;
    protected Stone stone;

    /**
     * Constructs a new Box with the given position.
     *
     * @param position The position of the box in the game grid.
     */
    public Box(int[] position) {
        this.position = position;
    }

    /**
     * Performs a skill associated with the box.
     * This method can be implemented based on the specific skill the box possesses.
     */
    public void skill() {
    }

    /**
     * Randomly positions a stone in the box.
     * This method can be used to simulate a random placement of stones in the box.
     */
    public void randomlyPosition() {
    }

    /**
     * Performs an action on the game board based on the box's strategy.
     *
     * @param board  The game board on which the action will be performed.
     * @param row    The row index for the action.
     * @param column The column index for the action.
     */
    public void action(Board board, int row, int column) {
    }

    /**
     * Gets the type of the box.
     *
     * @return The type of the box.
     */
    public String getType(){
        return this.type;
    }

    /**
     * Gets the color of the stone in the box.
     *
     * @return The color of the stone, or "Color por defecto" if no stone is present.
     */
    public String getColorStone() {
        if (this.stone != null) {
            return this.stone.getColor();
        } else {
            return "Color por defecto";
        }
    }

    /**
     * Gets the stone currently in the box.
     *
     * @return The stone in the box.
     */
    public Stone getStone(){
        return this.stone;
    }
    
    
    /**
     * Sets a new stone for the box.
     *
     * @param stone The stone to be set in the box.
     */
    public void setStone(Stone stone){
        this.stone = stone;
    }
}
