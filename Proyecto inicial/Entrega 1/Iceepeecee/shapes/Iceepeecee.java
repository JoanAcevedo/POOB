import java.util.*;

/**
 * Iceepeecee es un simulador basado en el problema F de la maraton
 * de programacion internacional 2022 Islands from the Sky
 *
 * @author Joan Acevedo
 * @version 09/09/2023
 */
public class Iceepeecee
{
    private HashMap<String, Island> islands = new HashMap<String, Island>();
    private HashMap<String, Fligth> fligths = new HashMap<String, Fligth>();
    private boolean isVisible;
    private boolean ok;
    private Rectangle territory;
    
    /**
     * Crea el territorio donde se realizara la simulacion
     * 
     * @param length, largo del territorio
     * @param width, ancho del territorio
     */
    public Iceepeecee(int length, int width){
        this.ok = false;
        this.territory = new Rectangle(length, width);
        territory.changeColor("white");
        this.ok = true;
    }
    
    /**
     * Agrega una isla al territorio
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     */
    public void addIsland(String color, int[][] polygon){
        this.ok = false;
        Island island = new Island(color, polygon);
        islands.put(color, island);
        if(this.isVisible){
            island.makeVisible();
        }
        this.ok = true;
    }
    
    /**
     * Agrega un vuelo al territorio
     * 
     * @param color, color del vuelo
     * @param from, punto de despegue
     * @param to, punto de llegada
     */
    public void addFligth(String color, int[] from, int[] to){
        this.ok = false;
        Fligth fligth = new Fligth(color, from, to);
        fligths.put(color, fligth);
        if(this.isVisible){
            fligth.makeVisible();
        }
        this.ok = true;
    }
    
    /**
     * Un vuelo realiza una fotografia
     * 
     * @param fligth, color del vuelo que va a tomar la foto
     * @param teta, angulo con el que se toma la foto
     */
    public void photograph(String fligth, int teta){
        this.ok = false;
        Fligth vuelo = fligths.get(fligth);
        if(vuelo != null){
            vuelo.makePhotograph(teta);   
        }
        this.ok = true;
    }
    
    /**
     * Se realiza una foto
     * 
     * @param teta, angulo de la foto
     */
    public void photograph(int teta){
        // en proceso...
    }
    
    /**
     * Elimina una isla del territorio
     * 
     * @param color, color que identifica a la isla
     */
    public void delIsland(String color){
        this.ok = false;
        Island isla = islands.get(color);
        if(isla != null){
            isla.makeInvisible();
            islands.remove(color);
        }
        this.ok = true;
    }
    
    /**
     * Elimina un vuelo del territorio
     * 
     * @param color, color que identifica el vuelo
     */
    public void delFligth(String color){
        this.ok = false;
        Fligth vuelo = fligths.get(color);
        if(vuelo != null){
            vuelo.makeInvisible();
            fligths.remove(color);
        }
        this.ok = true;
    }
    
    /**
     * Devuelve la locacion de una isla determinada
     * 
     * @param island, color que identifica a la isla
     * @return la locacion de la isla
     */
    public int[][] islandLocation(String island){
        this.ok = false;
        Island isla = islands.get(island);
        this.ok = true;
        return isla != null ? isla.getLocation() : null;   
    }
    
    /**
     * Devuelve la locacion de una isla determinada
     * 
     * @param island, color que identifica a la isla
     * @return la locacion de la isla
     */
    public int[][] fligthLocation(String fligth){
        this.ok = false;
        Fligth vuelo = fligths.get(fligth);
        this.ok = true;
        return vuelo != null ? vuelo.getLocation() : null;
        
    }
    
    /**
     * Devuelve el angulo de una foto en especifico
     * 
     * @param color, color de la foto
     * @return entero con el angulo de la foto
     */
    public int fligthCamera(String color){
        this.ok = false;
        Fligth vuelo = fligths.get(color);
        this.ok = true;
        return vuelo != null ? vuelo.anglePhotograph() : null;
    }
    
    /**
     * Hace visible todo lo que se encuentre dentro del territorio
     */
    public void makeVisible(){
        this.ok = false;
        this.territory.makeVisible();
        for(Island island : islands.values()){
            island.makeVisible();
        }
        for(Fligth fligth : fligths.values()){
            fligth.makeVisible();
        }
        this.isVisible = true;
        this.ok = true;
    }
    
    /**
     * Hace invisible todo lo que se encuentre dentro del territorio
     */
    public void makeInvisible(){
        this.ok = false;
        this.territory.makeInvisible();
        for(Island island : islands.values()){
            island.makeInvisible();
        }
        for(Fligth fligth : fligths.values()){
            fligth.makeInvisible();
        }
        this.isVisible = false;
        this.ok = true;
    }
    
    /**
     * Indica si la ultima accion se pudo realizar
     * <ol>
     * <li> True en caso de que se haya podido realizar la accion
     * <li> False en caso de que no se haya podido realizar la accion
     * </ol>
     */
    public boolean ok(){
        return this.ok;
    }

    /**
     * Finaliza la simulacion
     */
    public void finish(){
        System.exit(0);
    }

}
