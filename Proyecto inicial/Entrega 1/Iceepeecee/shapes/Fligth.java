
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
    private int[] from;
    private int[] to;
    private int[][] location;
    private static final int ALTURA = 20;
    private boolean isVisible;
    
    /**
     * Constructor del vuelo
     * 
     * @param color, color del vuelo
     * @param from, punto de inicio del vuelo
     * @param to, punto de llegada del vuelo
     */
    public Fligth(String color, int[] from, int[] to){
        this.color = color;
        this.from = from;
        this.to = to;
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
        location[0] = this.from;
        location[1] = this.to;
        return this.location;
    }
    
    /**
     * Crea una fotografia
     * 
     * @param int, angulo de la foto
     */
    public void makePhotograph(int teta){
        int lengthPhoto = trayectoria();
        double gamma = (double) teta;
        int widthPhoto = (int) Math.round(2 * this.ALTURA * Math.tan(Math.toRadians(gamma)));  
        this.photo = new Photograph(this.color, teta, lengthPhoto, widthPhoto);
        this.photo.makeVisible();
    }
    
    /**
     * Devuelve el angulo de la fotografia
     * 
     * @return entero con el angulo de la fotografia
     */
    public int anglePhotograph(){
        return this.photo.getTeta();
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

}
