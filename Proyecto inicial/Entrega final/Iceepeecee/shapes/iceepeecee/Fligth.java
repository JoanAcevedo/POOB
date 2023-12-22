package iceepeecee;

import shapes.Line;
import excepciones.IceepeeceeException;

import java.awt.Polygon;

/**
 * Vuelo que se realiza sobre Iceepeecee
 *
 * @author Joan Acevedo
 * @version 09/09/2023
 */
public class Fligth
{
    private String color;
    private Line trace;
    private Photograph photo;
    protected int[] from;
    protected int[] to;
    private int[][] location= new int[2][3];
    private boolean isVisible;
    private int lengthT;
    private int widthT;
    
    /**
     * Constructor del vuelo
     * 
     * @param color, color del vuelo
     * @param from, punto de inicio del vuelo
     * @param to, punto de llegada del vuelo
     * @param Iceepeecee, territorio al que pertenece el vuelo
     * @throws SALE_DEL_TERRITORIO si el vuelo se sale del territorio
     */
    public Fligth(String color, int[] from, int[] to, int lengthT, int widthT) throws IceepeeceeException{
        this.color = color;
        this.from = from;
        this.to = to;
        this.lengthT = lengthT;
        this.widthT = widthT;
        adentro();
        trace = new Line(color, from, to);
    }
    
    /**
     * Nos da la distancia de la trayectoria del avion
     * 
     * @return entero con la distancia total
     */
    public int trayectoria(){
        return this.trace.distanciaTotal();
    }
    
    /**
     * Devuelve la locacion del vuelo
     * 
     * @return una matriz con la locacion del vuelo
     */
    public int[][] getLocation(){
        this.location[0] = this.from;
        this.location[1] = this.to;
        return this.location;
    }
    
    /**
     * Devuelve el color del vuelo
     * 
     * @return String con el color del vuelo
     */
    public String getColor(){
        return this.color;
    }
    
    /**
     * Devuelve la fotografia
     * 
     * @return fotografia del vuelo
     */
    public Photograph getPhoto(){
        return this.photo;
    }
    
    /**
     * Crea una fotografia
     * 
     * @param float, angulo de la foto
     * @throws VUELO_LAZY si un vuelo Lazy ya tomo una foto
     */
    public void makePhotograph(float teta) throws IceepeeceeException{
        this.photo = new Photograph(this.color, teta, this.from, this.to);
        if(this.isVisible){
            this.photo.makeVisible();
        }
    }
    
    /**
     * Devuelve el angulo de la fotografia
     * 
     * @return entero con el angulo de la fotografia
     */
    public float anglePhotograph() throws IceepeeceeException{
        if(this.photo == null){
            throw new IceepeeceeException(IceepeeceeException.FOTO_NULL);
        }
        return this.photo.getTeta();
    }
    
    /**
     * Nos indica si el poligono de la isla esta dentro del poligono de la foto
     * 
     * @boolean True si esta adentro, False si no lo esta
     */
    public boolean comparacion(Polygon formaIsla){
        return this.photo.comparacion(formaIsla);
    }
    
     /**
     * Hace visible la isla
     */
    public void makeVisible(){
        isVisible = true;
        trace.makeVisible();
        if(this.photo != null){
            photo.makeVisible();    
        }
        
    }
    
    /**
     * Hace invisible la isla
     */
    public void makeInvisible(){
        trace.makeInvisible();
        isVisible = false;
    }  

    /*
     * Verifica si el vuelo esta dentro del territorio
     * 
     * @throws SALE_DEL_TERRITORIO si el vuelo esta por fuera del territorio
     */
    private void adentro() throws IceepeeceeException {
        for (int i = 0; i < location.length-1; i++) {
            if(from[i] > this.lengthT || to[i] > this.widthT || to[i] < 0 || from[i] < 0){
                throw new IceepeeceeException(IceepeeceeException.SALE_DEL_TERRITORIO);
            }
        }
    }
    
}
