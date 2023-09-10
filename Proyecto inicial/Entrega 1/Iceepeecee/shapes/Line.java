import java.awt.*;

/**
 * Es una linea reacta
 * 
 * @author Joan Acevedo 
 * @version 09/09/2023
 */
public class Line
{
    private float xInitial;
    private float yInitial;
    private float xFinal;
    private float yFinal;
    private String color;
    private boolean isVisible;

    /**
     * Constructor Line
     * 
     * @param color, color de la linea
     * @param xInitial, posicion en x inicial
     * @param yInitial, posicion en y inicial
     * @param xFinal, posicion en x final
     * @param yFinal, posicion en y final
     */
    public Line(String color, int[] from, int[] to){
        this.color = color;
        this.xInitial = from[0];
        this.yInitial = from[1];
        this.xFinal = to[0];
        this.yFinal = to[1];
    }

    /** Nos da el tamaño de la linea creada
     * 
     * @return int con valor del tamaño de la linea
     */
    public int distanciaTotal(){
        System.out.println(Math.abs(Math.sqrt(Math.pow(xFinal - xInitial, 2) + Math.pow(yFinal - yInitial, 2))));
        return (int) Math.abs(Math.sqrt(Math.pow(xFinal - xInitial, 2) + Math.pow(yFinal - yInitial, 2)));
    }
    
    /**
     * Hace visible la linea
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Hace invisible la linea
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /*
     * Dibuja la estructura de la linea
     */

    private void draw() {
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.geom.Line2D.Float(xInitial, yInitial, xFinal, yFinal));
            canvas.wait(10);
        }
    }

    /*
     * Borra la estructura de la linea
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}

