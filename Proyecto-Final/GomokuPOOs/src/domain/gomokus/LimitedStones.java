package domain.gomokus;

import java.io.Serializable;

import domain.GomokuPOOs;
import domain.players.*;

/**
 * Represents a variation of the Gomoku game where players have a limited number of stones.
 * Extends the GomokuPOOs class and provides specific functionality for the limited stones variation.
 */
public class LimitedStones extends GomokuPOOs implements Serializable{

    private static LimitedStones instance;

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private LimitedStones(){
        super();
    }

    /**
     * Retrieves the singleton instance of the LimitedStones class.
     *
     * @return The singleton instance of LimitedStones.
     */
    public static LimitedStones getInstance(){
        if(instance == null){
            instance = new LimitedStones();
        }
        return instance;
    }

    /**
     * Overrides the addPlayer method to customize player initialization for limited stones.
     * Adds a human player with limited stones to the game.
     *
     * @param name  The name of the human player.
     * @param color The color assigned to the human player.
     */
    @Override
    public void addPlayer(String name, String color){
        System.out.println("Es un juego de Piedras limitadas");
        Player player = new Human(name, color);
        player.deckOfLimitStones(this.board);
        players.put(name, player);
        if(GomokuPOOs.cont == 0){
            player.setTurn(true);
            GomokuPOOs.cont++;
        } else {
            player.setTurn(false);
        }
    }

}
