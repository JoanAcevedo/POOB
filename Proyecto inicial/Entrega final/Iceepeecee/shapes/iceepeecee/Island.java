package iceepeecee;

import shapes.Canvas;
import shapes.Circle;
import excepciones.IceepeeceeException;

import java.awt.Polygon;

/**
 * Isla perteneciente al territorio Iceepeecee
 *
 * @author Joan Acevedo
 * @version 29/10/2023
 */
public class Island
{
    protected String color;
    protected int[] xPoints;
    protected int[] yPoints;
    protected int nPoints;
    protected Polygon form;
    protected int[][] location;
    private boolean isVisible;
    private Circle centroC;
    private int[] centro;
    private int lengthT;
    private int widthT;
    
    /**
     * Constructor de la isla
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @param Iceepeecee, territorio al que pertenece la isla
     * @throws SALE_DEL_TERRITORIO si la isla se sale del territorio
     */
    public Island(String color, int[][] polygon, int lengthT, int widthT) throws IceepeeceeException {
        this.lengthT = lengthT;
        this.widthT = widthT;
        this.location = polygon;
        this.xPoints = new int[polygon.length];
        this.yPoints = new int[polygon.length];
        this.color = color;
        this.nPoints = polygon.length;
        for (int i = 0; i < polygon.length; i++) {
            xPoints[i] = polygon[i][0]; 
            yPoints[i] = polygon[i][1];
        }
        adentro();
        findCenter();
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
     * Devuelve el color de la isla
     * 
     * @return String con el color de la isla
     */
    public String getColor(){
        return this.color;
    }
    
    /**
     * Devuelve el poligono correspondiente a la isla
     * 
     * @return Polygon con la forma de la isla
     */
    public Polygon getForm(){
        return this.form;
    }
    
    /**
     * Crea un circulo que identifica que una isla esta fotografiada
     */
    public void estaFotografiada(){
        int x = this.centro[0];
        int y = this.centro[1];
        this.centroC = new Circle(x, y, "white");
        this.centroC.makeVisible();
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
    
    /*
     * Encuentra el centro del poligono
     */
    private void findCenter() {
        int sumX = 0;
        int sumY = 0;
        for (int i = 0; i < this.nPoints; i++) {
            sumX += xPoints[i];
            sumY += yPoints[i];
        }
        int centerX = sumX / this.nPoints;
        int centerY = sumY / this.nPoints;
        int[] centro = {centerX, centerY}; 
        this.centro = centro;
    }
    
    /*
     * Verifica si la isla esta dentro del territorio
     * 
     * @throws SALE_DEL_TERRITORIO si la isla esta por fuera del territorio
     */
    private void adentro() throws IceepeeceeException {
        for (int i = 0; i < location.length; i++) {
            if(xPoints[i] > this.lengthT || yPoints[i] > this.widthT || xPoints[i] < 0 || yPoints[i] < 0){
                throw new IceepeeceeException(IceepeeceeException.SALE_DEL_TERRITORIO);
            }
        }
    }
}
