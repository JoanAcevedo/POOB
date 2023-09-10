import java.awt.*;

/**
 * A hexagon that can be manipulated and that draws itself on a canvas.
 *
 * @author Joan Acevedo
 * @version 02/09/2023
 */
public class Hexagon
{
    private int sides = 6;
    private int radio = 30;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;

    /**
     * Create a new hexagon at default position with default color.
     */
    public Hexagon(){
        xPosition = 150;
        yPosition = 150;
        color = "red";
        isVisible = false;
    }
    
    /**
     * Make this hexagon visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this hexagon invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }

    /**
     * Move the hexagon a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the hexagon a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the hexagon a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the hexagon a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }
    
    /**
     * Move the hexagon horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the hexagon vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }
    
    /**
     * Slowly move the hexagon horizontally.
     * @param distance the desired distance in pixels
     */
    public void slowMoveHorizontal(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            xPosition += delta;
            draw();
        }
    }
    
    /**
     * Slowly move the hexagon vertically.
     * @param distance the desired distance in pixels
     */
    public void slowMoveVertical(int distance){
        int delta;

        if(distance < 0) {
            delta = -1;
            distance = -distance;
        } else {
            delta = 1;
        }

        for(int i = 0; i < distance; i++){
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Cambia la posicion del hexagono
     * @param xPosition, nueva posicion en x
     * @param yPosition, nueva posicion en y
     */
    public void changePosition(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }
    
    /**
     * Change the size to the new size
     * @param newSize the new radio in pixels. newSize must be >=0.
     */
    public void changeSize(int newSize) {
        erase();
        if(newSize >=0){
            this.radio = newSize;
            draw();
        }
    }
    
    /**
     * Change the color. 
     * @param color the new color. Valid colors are "red", "yellow", "blue", "green",
     * "magenta" and "black".
     */
    public void changeColor(String newColor){
        color = newColor;
        draw();
    }
    
    /**
     * Retorna el color del hexagono
     * @return color
     */
    public String getColor(){
        return this.color;
    }
    
    /*
     * Draw the hexagon with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            int[] xpoints = new int[sides];
            int[] ypoints = new int[sides];
            for(int i = 0; i < sides; i++){
                double theta = 2 * Math.PI * i / sides;
                xpoints[i] = (int) (xPosition + radio * Math.cos(theta));
                ypoints[i] = (int) (yPosition + radio * Math.sin(theta));  
            }
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 6));
            canvas.wait(10);
        }
    }
    
    /*
     * Erase the triangle on screen.
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
