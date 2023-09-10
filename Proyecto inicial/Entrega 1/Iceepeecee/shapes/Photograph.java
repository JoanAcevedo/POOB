
/**
 * Es una fotografia que se toma en el vuelo
 * 
 * @author Joan Acevedo 
 * @version 09/09/2023
 */
public class Photograph
{
    private String color;
    private int teta;
    private boolean isVisible;
    
    /**
     * Crea una fotografia
     */
    public Photograph(String color, int teta, int[] from, int[] to){
        this.color = color;
        this.teta = teta;
        // en proceso...
    }
    
    /**
     * Devuelve el angulo de la foto
     * 
     * @return entero con el angulo de la foto
     */
    public int getTeta(){
        return this.teta;
    }
    
    /**
     * Hace visible la fotografia
     */
    public void makeVisible(){
        this.isVisible = true;
        //en proceso...
    }
    
    /**
     * Hace invisible la fotografia
     */
    public void makeInvisible(){
        this.isVisible = false;
        //en progreso...
    }

}
