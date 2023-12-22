package iceepeecee;

import shapes.Canvas;

import java.awt.Polygon;

/**
 * Es una fotografia que se toma en el vuelo
 * 
 * @author Joan Acevedo 
 * @version 20/10/2023
 */
public class Photograph
{
    private String color;
    private float teta;
    private int alturaInicial;
    private int alturaFinal;
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private Polygon sombra;
    private boolean isVisible;
    
    /**
     * Crea una fotografia
     */
    public Photograph(String color, float teta, int[] from, int[] to){
        this.color = color;
        this.teta = teta;
        this.fromX = from[0];
        this.toX = to[0];
        this.fromY = from[1];
        this.toY = to[1];
        this.alturaInicial = from[2];
        this.alturaFinal = to[2];
        crearSombra(calcularSombra());
    }
    
    /**
     * Devuelve el angulo de la foto
     * 
     * @return entero con el angulo de la foto
     */
    public float getTeta(){
        return this.teta;
    }
    
    /**
     * Nos indica si el poligono de la isla esta dentro del poligono de la foto
     * 
     * @boolean True si esta adentro, False si no lo esta
     */
    public boolean comparacion(Polygon formaIsla){
       for (int i = 0; i < formaIsla.npoints; i++) {
            int x = formaIsla.xpoints[i];
            int y = formaIsla.ypoints[i];
            if (!this.sombra.contains(x, y)) {
                return false;
            }
        }
        return true; 
    }
    
    /**
     * Hace visible la fotografia
     */
    public void makeVisible(){
        this.isVisible = true;
        draw();
    }
    
    /**
     * Hace invisible la fotografia
     */
    public void makeInvisible(){
        erase();
        this.isVisible = false;
    }

    /*
     * Calcula los puntos necesarios para poder construir la sombra de la fotografia
     * 
     * @return int[] con todos los puntos calculados
     */
    private int[] calcularSombra(){
        double primeraAnchura = alturaInicial * Math.tan(Math.toRadians(teta /2));
        double segundaAnchura = alturaFinal * Math.tan(Math.toRadians(teta /2));
        double primeraPendiente = (double) (toY -fromY) / (toX- fromX);
        double segundaPendiente = primeraPendiente / -1;
        int[] puntos = new int[8];
        puntos[0] = (int) (fromX - primeraAnchura);
        puntos[1] = (int) (fromX + primeraAnchura);
        puntos[2] = (int) (toX + segundaAnchura);
        puntos[3] = (int) (toX - segundaAnchura);
        puntos[4] = (int) (fromY - primeraAnchura * segundaPendiente);
        puntos[5] = (int) (fromY + primeraAnchura * segundaPendiente);
        puntos[6] = (int) (toY + segundaAnchura * segundaPendiente);
        puntos[7] = (int) (toY - segundaAnchura * segundaPendiente);
        return puntos;
    }
    
    /*
     * Crea la sombra de la fotografia
     * 
     * @param puntos, int[] con los puntos necesarios para construtir la sombra
     */
    private void crearSombra(int[] puntos){
        int[] xPuntos = new int[4];
        xPuntos[0] = puntos[0];
        xPuntos[1] = puntos[1];
        xPuntos[2] = puntos[2];
        xPuntos[3] = puntos[3];
        int[] yPuntos = new int[4];
        yPuntos[0] = puntos[4];
        yPuntos[1] = puntos[5];
        yPuntos[2] = puntos[6];
        yPuntos[3] = puntos[7];
        sombra = new Polygon(xPuntos, yPuntos, 4);
    }
    
    /*
     * Dibuja la sombra de la fotografia
     */
    private void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, this.sombra);
            canvas.wait(10);
        }
    }

    /*
     * Borra la sombra de la fotografia
     */
    private void erase(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
    
}
