package domain.boxes;

import java.io.Serializable;

/**
 * The NormalBox class represents a standard box in a game, typically containing a stone.
 * It extends the Box class and implements the Serializable interface for serialization support.
 */
public class NormalBox extends Box implements Serializable{

    /**
     * Constructs a new NormalBox with the given position.
     *
     * @param position The position of the NormalBox in the game grid.
     */
    public NormalBox(int[] position) {
        super(position);
        this.type = "normal";
    }

}
