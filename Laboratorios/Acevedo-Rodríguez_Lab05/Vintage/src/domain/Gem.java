package domain;

/**
 * Gem represents a gemstone that occupies a specific cell on the game board.
 * @author Joan Acevedo / Laura Rodriguez
 * @version 18/11/2023
 */
public class Gem {

    private String color;
    
    /**
     * Constructs a gem with a specific color.
     * @param color The color of the gem.
     */
    public Gem(String color) {
        this.color = color;
        if(color.equals("x")){
            this.color = "Colors/Geliminada.png"; 
        }
    }
    
    /**
     * Gets the color of the gem.
     * @return The color of the gem.
     */
    public String getColor() {
        return color;
    }
    
}
