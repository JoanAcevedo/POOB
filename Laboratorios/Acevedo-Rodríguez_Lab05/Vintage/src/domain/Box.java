package domain;

import java.util.Random;

/**
 * Represents a cell on the game board that contains a gem.
 * @author Joan Acevedo / Laura Rodriguez
 * @version 11/18/2023
 */
public class Box {

    private boolean state;
    private Gem gem;
    private int[] position;

    /**
     * Constructor for a box that requires a position.
     * @param position The coordinates of the box.
     */
    public Box(int[] position) {
        this.state = false;
        this.position = position;
        generateGem();
    }

    /**
     * Gets the position of the box.
     * @return An array of integers representing the position of the box.
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Indicates whether the gems in this box have been destroyed.
     * @return False if not destroyed, true if already destroyed.
     */
    public boolean getState() {
        return state;
    }

    /**
     * Gets the gem corresponding to this box.
     * @return The gem belonging to the box.
     */
    public Gem getGem() {
        return gem;
    }

    /**
     * Allows changing the gem in this box.
     * @param gem The new gem.
     */
    public void setGem(Gem gem) {
        this.gem = gem;
    }
    
    /**
     * Gets the color of the gem in this box.
     * @return The color of the gem.
     */
    public String getGemColor(){
        return gem.getColor();
    }

    /*
     * Generates a gem of a random color in this box.
     */
    private void generateGem(){
        String[] colors = {"Colors/Gnaranja.png", "Colors/Gamarilla.png", "Colors/Groja.png", "Colors/Gazul.png",
                            "Colors/Gblanco.png", "Colors/Gverde.png", "Colors/Grosa.png"};
        Random random = new Random();
        int indexRandom = random.nextInt(colors.length);
        String colorRandom = colors[indexRandom];
        gem = new Gem(colorRandom);
    }
}

