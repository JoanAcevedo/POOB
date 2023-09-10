import java.awt.*;
import java.util.Random;

/**
 * A triangle that can be manipulated and that draws itself on a canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */

public class Triangle{
    
    public static int VERTICES=3;
    
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private String color;
    private boolean isVisible;
    private float area;
    private Circle punto;
    private String[] colorList = new String[]{"black", "red", "blue", "yellow", "green", "magenta"};
    private Random random = new Random();

    /**
     * Create a new triangle at default position with default color.
     */
    public Triangle(){
        height = 30;
        width = 40;
        xPosition = 150;
        yPosition = 150;
        color = "green";
        isVisible = false;
    }
    
    /**
     * Crea un triangulo al azar a partir del area dada
     * @param area, el area del triangulo deseado
     */
    public Triangle(int area){
        this.area = area;
        this.width = random.nextInt(150);
        this.height = (2 * area) / this.width;
        xPosition = 140;
        yPosition = 150;
        color = "green";
        isVisible = false;
    }
    
    /**
     * Cambia el color de un triangulo un numero determinado de veces
     * @param times, numero de veces que cambiara el color
     */
    public void blink(int times){
        if(times > 0){
            for(int i = 0; i < times; i++){
                randomChangeColor();
                if(i < times-1){
                    this.color = "white";
                    draw();
                    draw();
                }     
                draw();
            }     
        }
    }

    /**
     * Convierte un triangulo a uno equilatero, manteniendo su misma area(Aprox.)
     */
    public void equilateral(){
        this.width = (int) Math.sqrt((4 * this.area) / Math.sqrt(3));
        this.height = (int) (2 * this.area) / this.width;
        draw();
    }
    
    /**
     * El triangulo hara espirales dejando un rastro de puntos
     * en la esquina mas alejada de su punto de inicio
     * @param cantidad, numero de espirales que hara
     */
    public void espiral(int cantidad){
        int distance = 100;
        for(int i = 0; i < cantidad; i++){
            slowMoveVertical(-distance);
            slowMoveHorizontal(-distance);
            punto = new Circle(this.xPosition, this.yPosition);
            punto.changeSize(15);
            punto.changeColor(this.color);
            punto.makeVisible();
            slowMoveVertical(distance);
            slowMoveHorizontal(distance);
            randomChangeColor();
            distance -= 25;
        }
    }
    
    /**
     * Nos da el area del triangulo
     * @return area del triangulo 
     */
    public float getArea(){
        this.area = (this.height * this.width)/ 2;
        return area;
    }
    
    /**
     * Make this triangle visible. If it was already visible, do nothing.
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Make this triangle invisible. If it was already invisible, do nothing.
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /**
     * Move the triangle a few pixels to the right.
     */
    public void moveRight(){
        moveHorizontal(20);
    }

    /**
     * Move the triangle a few pixels to the left.
     */
    public void moveLeft(){
        moveHorizontal(-20);
    }

    /**
     * Move the triangle a few pixels up.
     */
    public void moveUp(){
        moveVertical(-20);
    }

    /**
     * Move the triangle a few pixels down.
     */
    public void moveDown(){
        moveVertical(20);
    }

    /**
     * Move the triangle horizontally.
     * @param distance the desired distance in pixels
     */
    public void moveHorizontal(int distance){
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Move the triangle vertically.
     * @param distance the desired distance in pixels
     */
    public void moveVertical(int distance){
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Slowly move the triangle horizontally.
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
     * Slowly move the triangle vertically.
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
     * Change the size to the new size
     * @param newHeight the new height in pixels. newHeight must be >=0.
     * @param newWidht the new width in pixels. newWidht must be >=0.
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        if(newHeight >=0 || newWidth >= 0){
            height = newHeight;
            width = newWidth;
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

    /*
     * Draw the triangle with current specifications on screen.
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
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
    
    /*
     * Genera un color aleatorio
     */
    private void randomChangeColor(){
        int aleatorio = random.nextInt(6);
        this.color = colorList[aleatorio];
        if(this.color == colorList[aleatorio]){
            aleatorio = random.nextInt(6);
            this.color = colorList[aleatorio];
            draw();             
        }
    }
}
