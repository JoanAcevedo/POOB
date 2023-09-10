import java.awt.Polygon;

/**
 * Isla perteneciente al territorio Iceepeecee
 *
 * @author Joan Acevedo
 * @version 09/09/2023
 */
public class Island
{
    private String color;
    private int[] xPoints;
    private int[] yPoints;
    private int nPoints;
    private Polygon form;
    private int[][] location;
    private boolean isVisible;
    
    /**
     * Constructor de la isla
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     */
    public Island(String color, int[][] polygon){
        this.location = polygon;
        this.xPoints = new int[polygon.length];
        this.yPoints = new int[polygon.length];
        this.color = color;
        this.nPoints = polygon.length;
        for (int i = 0; i < polygon.length; i++) {
            xPoints[i] = polygon[i][0]; 
            yPoints[i] = polygon[i][1];
        }
        this.form = new Polygon(xPoints, yPoints, nPoints);
    }
    
    /**
     * Devuelve la locacion de la isla
     * 
     * @return una matriz con la locacion de la isla
     */
    public int[][] getLocation(){
        return this.location;
    }
    
    /**
     * Hace visible la isla
     */
    public void makeVisible(){
        isVisible = true;
        draw();
    }
    
    /**
     * Hace invisible la isla
     */
    public void makeInvisible(){
        erase();
        isVisible = false;
    }
    
    /*
     * Dibuja la estructura de la isla
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, this.form);
            canvas.wait(10);
        }
    }

    /*
     * Borra la estructura de la isla
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
