package domain.boxes;

import java.io.Serializable;
import domain.*;
import domain.players.Player;
import domain.stones.Stone;
import domain.stones.Heavy;
import domain.stones.Temporary;

/**
 * The Golden class represents a special box in a game that provides unique actions.
 * It inherits from the Box class and implements the Serializable interface.
 */
public class Golden extends Box implements Serializable{

    /**
     * Constructs a new Golden box with the given position.
     *
     * @param position The position of the Golden box in the game grid.
     */
    public Golden(int[] position) {
        super(position);
        this.type = "golden";
        System.out.println("X de la poscion de una golden: " + this.position[0]);
        System.out.println("Y de la poscion de una golden: " + this.position[1]);
        System.out.println("-------------------------------------------------");
    }

     /**
     * Performs a special action on the game board when a player interacts with the Golden box.
     * This action involves adding a randomly selected stone to the player's collection.
     *
     * @param board  The game board on which the action will be performed.
     * @param row    The row index for the action.
     * @param column The column index for the action.
     */
    public void action(Board board, int row, int column) {
        Player playerInTurn = board.getGomokuPOOs().getPlayerInTurn();
        Stone newStone = randomlyStone(playerInTurn.getColor());
        playerInTurn.addStone(newStone);
    }

    /**
     * Generates a random stone based on the player's color.
     * The stone can be either a Heavy stone or a Temporary stone.
     *
     * @param color The color of the stone to be generated.
     * @return A randomly selected stone (Heavy or Temporary).
     */
    public Stone randomlyStone(String color) {
        Stone stone = null;
        int tipoFicha = (int) (Math.random() * 2); // Generar un número entre 0 y 2
        if (tipoFicha == 0) {
            stone = new Heavy(color);
            System.out.println("Se agrego una p. Heavy");
        } else if (tipoFicha == 1 || tipoFicha == 2) {
            stone = new Temporary(color);
            System.out.println("Se agrego una p. Temporary");
        } 
        return stone;
    }

}
