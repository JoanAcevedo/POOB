package domain.boxes;

import java.io.Serializable;
import java.util.Random;
import domain.*;

/**
 * The Teleport class represents a special type of box in a game that, when activated,
 * teleports the stone within it to a random position on the game board.
 * It extends the Box class and implements the Serializable interface for serialization support.
 */
public class Teleport extends Box implements Serializable{

    private int[] positionFinal;
    private Board board;

    /**
     * Constructs a new Teleport with the given position and game board.
     *
     * @param position The position of the teleport box in the game grid.
     * @param board    The game board on which the teleportation occurs.
     */
    public Teleport(int[] position, Board board) {
        super(position);
        this.board = board;
        this.type = "teleport";
        System.out.println("X de la poscion de una teleport: " + this.position[0]);
        System.out.println("Y de la poscion de una teleport: " + this.position[1]);
        System.out.println("-------------------------------------------------");
    }

    /**
     * Performs the action associated with the teleport, which involves
     * teleporting the stone within it to a random position on the game board.
     */
    public void action(Board board, int row, int column) {
        int randomRow = generateRandomPosition(0, board.getHeight() - 1);
        int randomColumn = generateRandomPosition(0, board.getWidth() - 1);
        positionFinal = new int[]{randomRow, randomColumn};
        this.board.getBox(randomRow, randomColumn).setStone(this.stone);
        getStone().setPosition(positionFinal);
        this.stone = null; 
    }
    
    /**
     * Generates a random position within the specified range.
     *
     * @param min The minimum value of the range.
     * @param max The maximum value of the range.
     * @return A randomly generated position within the specified range.
     */
    private int generateRandomPosition(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
