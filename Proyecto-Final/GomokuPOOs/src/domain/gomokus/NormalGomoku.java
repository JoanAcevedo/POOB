package domain.gomokus;

import java.io.Serializable;

import domain.*;

/**
 * Represents the standard version of the Gomoku game.
 * Extends the GomokuPOOs class and provides specific functionality for the normal Gomoku game.
 */
public class NormalGomoku extends GomokuPOOs implements Serializable{

    private static NormalGomoku instance;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private NormalGomoku(){
        super();
    }

    /**
     * Retrieves the singleton instance of the NormalGomoku class.
     *
     * @return The singleton instance of NormalGomoku.
     */
    public static NormalGomoku getInstance(){
        if(instance == null){
            instance = new NormalGomoku();
        }
        return instance;
    }

    /**
     * Creates a new game board with the specified dimensions.
     *
     * @param height The height of the board.
     * @param width  The width of the board.
     */
    public void addBoard(int height, int width){
        this.board = new Board(this, height, width);
    }

}
