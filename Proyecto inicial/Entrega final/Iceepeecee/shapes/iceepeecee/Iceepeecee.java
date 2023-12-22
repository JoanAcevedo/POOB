package iceepeecee;

import shapes.Rectangle;
import excepciones.IceepeeceeException;

import java.util.*;

/**
 * Iceepeecee es un simulador basado en el problema F de la maraton
 * de programacion internacional 2022 Islands from the Sky
 *
 * @author Joan Acevedo
 * @version 29/10/2023
 */
public class Iceepeecee
{
    private HashMap<String, Island> islands = new HashMap<String, Island>();
    private HashMap<String, Fligth> fligths = new HashMap<String, Fligth>();
    private boolean isVisible;
    private boolean ok;
    private int length, width;
    private Rectangle territory;
    private final String[] COLORS ={"red", "black", "blue", "yellow", "green", "magenta", "cyan", "gray", "orange", "pink"};
    
    /**
     * Crea el territorio donde se realizara la simulacion
     * 
     * @param length, largo del territorio, valores entre (1 - 700)
     * @param width, ancho del territorio, valores entre (1 - 700)
     * @throws TERRITORIO_IMPOSIBLE si los valores de length y width no son validos
     */
    public Iceepeecee(int length, int width){
        this.ok = false;
        try{
            this.territory = new Rectangle(length, width);
            this.length = length;
            this.width = width;
            territory.changeColor("white");
            this.ok = true;
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Constructor con la entrada apropiada a la del maraton
     * 
     * @param islands, conjunto de islas a ingresar
     * @param fligths, conjuntos de vuelos a ingresar
     * @throws SALE_DEL_TERRITORIO si las coordenadas de las islas o vuelos no son validos
     */
    public Iceepeecee(int [][][] islands, int [][][] fligths){
        try{
            this.territory = new Rectangle(500, 500);
            this.length = 500;
            this.width = 500;
            territory.changeColor("white");
            for(int i = 0; i < islands.length; i++){
                String color = anyColor();
                while(this.islands.containsKey(color) && this.islands.size() < this.COLORS.length){
                    color = anyColor();
                }
                Island isla = new Island(color, islands[i], this.length, this.width);
                this.islands.put(color, isla);
            }
            for(int i = 0; i < fligths.length; i++){
                int[] from = fligths[i][0];
                int[] to = fligths[i][1];
                String color = anyColor();
                while(this.fligths.containsKey(color) && this.fligths.size() < this.COLORS.length){
                    color = anyColor();
                }
                Fligth vuelo = new Fligth(color, from, to, this.length, this.width);
                this.fligths.put(vuelo.getColor(), vuelo);
            }
            this.ok = true;
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
            this.ok = false;
        }
    }

    /**
     * Agrega una isla al territorio
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @throws ISLA_REPETIDA si ya existe otra isla con el mismo color
     * @throws SALE_DEL_TERRITORIO si las coordenadas de la isla no son validos
     */
    public void addIsland(String color, int[][] polygon){
        this.ok = false;
        try{
            if(!islands.containsKey(color)){
                Island island = new Island(color, polygon, this.length, this.width);
                islands.put(color, island);
                if(this.isVisible){
                    island.makeVisible();
                }
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.ISLA_REPETIDA);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Agrega una isla de un determinado tipo al territorio
     * 
     * @param type, 'n' normal, 'f' fixed, 's' surprising
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @throws ISLA_REPETIDA si ya existe otra isla con el mismo color
     * @throws TIPO_INEXISTENTE_ISLA si ingreso un tipo de isla invalido
     * @throws SALE_DEL_TERRITORIO si las coordenadas de la isla no son validos
     */
    public void addIsland(String type, String color, int[][] polygon){
        this.ok = false;
        try{
            if(!islands.containsKey(color)){
                selectIsland(type, color, polygon); 
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.ISLA_REPETIDA);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Agrega un vuelo al territorio
     * 
     * @param color, color del vuelo
     * @param from, punto de despegue
     * @param to, punto de llegada
     * @throws VUELO_REPETIDO si ya existe otro vuelo con el mismo color
     * @throws SALE_DEL_TERRITORIO si las coordenadas del vuelo no son validos
     */
    public void addFligth(String color, int[] from, int[] to) {
        this.ok = false;
        try{
            if(!fligths.containsKey(color)){
                Fligth fligth = new Fligth(color, from, to, this.length, this.width);
                fligths.put(color, fligth);
                if(this.isVisible){
                    fligth.makeVisible();
                }
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.VUELO_REPETIDO);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Agrega un vuelo de un detrminado tipo al territorio
     * 
     * @param type, 'n' normal, 'l' lazy, 'f' flat
     * @param color, color del vuelo
     * @param polygon, puntos con coordenadas del vuelo
     * @throws VUELO_REPETIDO si ya existe otro vuelo con el mismo color
     * @throws TIPO_INEXISTENTE_VUELO si ingreso un tipo de vuelo invalido
     * @throws SALE_DEL_TERRITORIO si las coordenadas del vuelo no son validos
     */
    public void addFligth(String type, String color, int[] from, int[] to) {
        this.ok = false;
        try{
            if(!fligths.containsKey(color)){
                selectFligth(type, color, from, to);
                this.ok = true;
            } else {
                throw new IceepeeceeException(IceepeeceeException.VUELO_REPETIDO);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Un vuelo realiza una fotografia
     * 
     * @param fligth, color del vuelo que va a tomar la foto
     * @param teta, angulo entero entre 1 y 180
     * @throws ANGULO_INCORRECTO si el angulo no tiene un valor valido
     * @throws VUELO_NULL si el vuelo no existe
     * @throws VUELO_LAZY si un vuelo Lazy ya tomo una foto
     */
    public void photograph(String fligth, int teta) {
        this.ok = false;
        Fligth vuelo = fligths.get(fligth);
        try{
            if(vuelo != null){
                if(teta > 0 && teta <= 180){
                    vuelo.makePhotograph(teta);
                    this.ok = true;
                } else{
                    throw new IceepeeceeException(IceepeeceeException.ANGULO_INCORRECTO);
                }
            } else{
                throw new IceepeeceeException(IceepeeceeException.VUELO_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Todos los vuelos realizan una foto
     * 
     * @param teta, angulo entero de la foto entre 1 y 180
     * @throws ANGULO_INCORRECTO si el angulo no tiene un valor valido
     * @throws VUELO_LAZY si un vuelo Lazy ya tomo una foto
     */
    public void photograph(int teta) {
        this.ok = false;
        try{
            if(teta > 0 && teta <= 180){
                for(Map.Entry<String, Fligth> entry : fligths.entrySet()){
                    Fligth flight = entry.getValue();
                    flight.makePhotograph(teta);
                }
                this.ok = true;
            } else {
                throw new IceepeeceeException(IceepeeceeException.ANGULO_INCORRECTO);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Todos los vuelos realizan una foto
     * 
     * @param teta, angulo decimal de la foto entre 1 y 180
     * @throws ANGULO_INCORRECTO si el angulo no tiene un valor valido
     * @throws VUELO_LAZY si un vuelo Lazy ya tomo una foto
     */
    public void photograph(float teta){
        this.ok = false;
        try{
            if(teta > 0 && teta <= 180){
                for(Map.Entry<String, Fligth> entry : fligths.entrySet()){
                    Fligth flight = entry.getValue();
                    flight.makePhotograph(teta);
                }
                this.ok = true;
            } else {
                throw new IceepeeceeException(IceepeeceeException.ANGULO_INCORRECTO);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Elimina una isla del territorio
     * 
     * @param color, color que identifica a la isla
     * @throws ISLA_FIXED si trata de borrar una isla tipo Fixed
     * @throws ISLA_NULL si trata de borrar una isla que no existe
     */
    public void delIsland(String color){
        this.ok = false;
        Island isla = islands.get(color);
        try{
            if(isla != null){
                if(!(isla instanceof Fixed)){ // si no es una instancia de Fixed
                    isla.makeInvisible();
                    islands.remove(color);
                    this.ok = true;
                }else{
                    throw new IceepeeceeException(IceepeeceeException.ISLA_FIXED);
                }
            } else {
                throw new IceepeeceeException(IceepeeceeException.ISLA_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Elimina un vuelo del territorio
     * 
     * @param color, color que identifica el vuelo
     * @throws VUELO_NULL si trata de borra un vuelo que no existe
     */
    public void delFligth(String color){
        this.ok = false;
        Fligth vuelo = fligths.get(color);
        try{
            if(vuelo != null){
                vuelo.makeInvisible();
                fligths.remove(color);
                this.ok = true;
            } else {
                throw new IceepeeceeException(IceepeeceeException.VUELO_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    /**
     * Devuelve la locacion de una isla determinada
     * 
     * @param island, color que identifica a la isla
     * @return la locacion de la isla
     * @throws ISLA_NULL si la isla que no existe
     */
    public int[][] islandLocation(String island){
        this.ok = false;
        int[][] locacion = null;
        Island isla = islands.get(island);
        try{
            if(isla != null){
                locacion = isla.getLocation();
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.ISLA_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
            this.ok = false;
        }
        return locacion; 
    }
    
    /**
     * Devuelve la locacion de un vuelo determinado
     * 
     * @param island, color que identifica al vuelo
     * @return la locacion del vuelo
     * @throws VUELO_NULL si el un vuelo que no existe
     */
    public int[][] fligthLocation(String fligth){
        this.ok = false;
        int[][] locacion = null;
        Fligth vuelo = fligths.get(fligth);
        try{
            if(vuelo != null){
                locacion = vuelo.getLocation();
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.VUELO_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        return locacion; 
        
    }
    
    /**
     * Devuelve el angulo de una foto en especifico
     * 
     * @param color, color de la foto
     * @return entero con el angulo de la foto
     * @throws VUELO_NULL si el un vuelo que no existe
     * @throws FOTO_NULL si el vuelo no ha realizado una foto
     */
    public float fligthCamera(String color){
        this.ok = false;
        Fligth vuelo = fligths.get(color);
        float angulo = 0;
        this.ok = true;
        try{
            if(vuelo != null){
                angulo = vuelo.anglePhotograph();
                this.ok = true;
            } else{
                throw new IceepeeceeException(IceepeeceeException.VUELO_NULL);
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        return angulo; 
    }
    
    /**
     * Devuelve un arreglo con los identificadores de las islas (color)
     * 
     * @return String[] con las islas (color)
     */
    public String[] islands(){
        Set<String> set = this.islands.keySet();
        String[] conjutoIslas = set.toArray(new String[set.size()]);
        return conjutoIslas;
    }
    
    /**
     * Devuelve un arreglo con los identificadores de las islas (color)
     * 
     * @return String[] con las islas (color)
     */
    public String[] fligths(){
        Set<String> set = this.fligths.keySet();
        String[] conjutoVuelos = set.toArray(new String[set.size()]);
        return conjutoVuelos;
    }
    
    /**
     * Nos indica cuales islas estan siendo observadas por una fotografia
     * 
     * @String[] con los nombres de las islas observadas
     */
    public String[] observedIslands(){
        String[] islas = islands();
        Set<String> islasVistas = new HashSet<>();
        boolean inside = false;
        for(Fligth fligth : fligths.values()){
            for(int i = 0; i < islas.length ; i++){
                inside = fligth.comparacion(this.islands.get(islas[i]).getForm());
                if(inside){
                    islasVistas.add(this.islands.get(islas[i]).getColor());
                }
            }
        }
        String[] islasObservadas = islasVistas.toArray(new String[islasVistas.size()]);
        return islasObservadas;
    }
    
    /**
     * Nos indica cuando un vuelo es inutil, es decir cuando no
     * captura una isla por completo
     * 
     * @String[] con los nombres de los vuelos inutiles
     */
    public String[] uselessFlights(){
        String[] islas = islands();
        Set<String> vuelosInutiles = new HashSet<>();
        boolean inside = false;
        for(Fligth fligth : fligths.values()){
            for(int i = 0; i < islas.length ; i++){
                if(!inside){
                    // Si inside es False, entonces la foto no ha captado la isla [i] 
                    inside = fligth.comparacion(this.islands.get(islas[i]).getForm()); 
                }
            }
            if(!inside){
                // Si inside es True entonces capto por lo menos una isla completa
                vuelosInutiles.add(fligth.getColor());
            }
        }
        String[] vuelosTontos = vuelosInutiles.toArray(new String[vuelosInutiles.size()]);
        return vuelosTontos;
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
    
    /**
     * Pinta las islas que son visibles ante un vuelo con una marca que las identifica
     */
    public void pintarIsla(){
        String[] islasVisibles = observedIslands();
        if(this.isVisible && this.fligths.size() != 0){
            for(int i = 0; i < islasVisibles.length; i++){
                islands.get(islasVisibles[i]).estaFotografiada();
            }
        }
    }
    
    /**
     * Realiza las fotografias de los vuelos invisibles
     */
    public void hacerFotosInvisibles(){
        for(Fligth f : this.fligths.values()){
            f.getPhoto().makeInvisible();
        }
    }

    /*
     * Crea una isla segun el tipo ingresado
     * 
     * @param type, 'n' normal, 'f' fixed, 's' surprising
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @throws ISLA_REPETIDA si ya existe otra isla con el mismo color
     * @throws TIPO_INEXISTENTE_ISLA si ingreso un tipo de isla invalido
     * @throws SALE_DEL_TERRITORIO si las coordenadas de la isla no son validos
     */
    private void selectIsland(String type, String color, int[][] polygon) throws IceepeeceeException{
        switch(type){
                case "n":
                    NormalIsland island = new NormalIsland(color, polygon, this.length, this.width);
                    islands.put(color, island);
                    if(this.isVisible){
                        island.makeVisible();
                    }
                    break;
                case "s":
                    Surprising surprising = new Surprising(color, polygon, this.length, this.width);
                    islands.put(color, surprising);
                    if(this.isVisible){
                        surprising.makeVisible();
                    }
                    break;
                case "f":
                    Fixed fixed = new Fixed(color, polygon, this.length, this.width);
                    islands.put(color, fixed);
                    if(this.isVisible){
                        fixed.makeVisible();
                    }
                    break;
                default:
                    throw new IceepeeceeException(IceepeeceeException.TIPO_INEXISTENTE_ISLA);
            }
    }
    
    /*
     * Crea un vuelo de un detrminado tipo al territorio
     * 
     * @param type, 'n' normal, 'l' lazy, 'f' flat
     * @param color, color del vuelo
     * @param polygon, puntos con coordenadas del vuelo
     * @throws VUELO_REPETIDO si ya existe otro vuelo con el mismo color
     * @throws TIPO_INEXISTENTE_VUELO si ingreso un tipo de vuelo invalido
     * @throws SALE_DEL_TERRITORIO si las coordenadas del vuelo no son validos
     */
    private void selectFligth(String type, String color, int[] from, int[] to) throws IceepeeceeException{
        switch(type){
                case "n":
                    NormalFligth fligth = new NormalFligth(color, from, to, this.length, this.width);
                    fligths.put(color, fligth);
                    if(this.isVisible){
                        fligth.makeVisible();
                    }
                    break;
                case "l":
                    Lazy lazy = new Lazy(color, from, to, this.length, this.width);
                    fligths.put(color, lazy);
                    if(this.isVisible){
                        lazy.makeVisible();
                    }
                    break;
                case "f":
                    Flat flat = new Flat(color, from, to, this.length, this.width);
                    fligths.put(color, flat);
                    if(this.isVisible){
                        flat.makeVisible();
                    }
                    break;
                default:
                    throw new IceepeeceeException(IceepeeceeException.TIPO_INEXISTENTE_VUELO);
            }
    }
    
    /*
     * Genera un color aleatorio
     * 
     * @return String con el nombre del color generado
     */
    private String anyColor(){
        int numero = (int)(Math.random()*this.COLORS.length);
        String color = COLORS[numero];
        return color;
    }
}
