package domain.gomokus;

import java.io.Serializable;

import domain.GomokuPOOs;

/**
 * Represents a variation of the Gomoku game with a quick time mode.
 * Extends the GomokuPOOs class and provides specific functionality for the quick time mode.
 */
public class Quicktime extends GomokuPOOs implements Serializable{

    private static Quicktime instance;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private Quicktime(){
        super();
    }

    /**
     * Retrieves the singleton instance of the Quicktime class.
     *
     * @return The singleton instance of Quicktime.
     */
    public static Quicktime getInstance(){
        if(instance == null){
            instance = new Quicktime();
        }
        return instance;
    }

}
