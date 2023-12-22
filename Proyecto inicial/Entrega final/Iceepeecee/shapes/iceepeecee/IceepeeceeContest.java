package iceepeecee;

import excepciones.IceepeeceeException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Write a description of class IceepeeceeContest here.
 * 
 * @author Joan Acevedo 
 * @version 16/10/2023
 */
public class IceepeeceeContest
{
    private float angle;
    private Iceepeecee prueba;
    private int[][][] islands;
    private int[][][] fligths;
    
    /**
     * Constructor for objects of class IceepeeceeContest
     */
    public IceepeeceeContest(){

    }
    
    /**
     * @return float con el angulo apropiado. Si el angulo es "-1", significa que es imposible
     */
    public float solve(int[][][] islands, int[][][] fligths) throws IceepeeceeException {
        float result = 0;
        prueba = new Iceepeecee(islands, fligths);
        this.islands = islands;
        this.fligths = fligths;
        result = resolve();
        this.angle = result;
        return result;
    }
    
    public void simulate() throws IceepeeceeException {
        Timer timer = new Timer();
        prueba = new Iceepeecee(this.islands, this.fligths);
        prueba.makeVisible();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                prueba.photograph(angle);
                prueba.makeVisible();
            }
        };
        timer.schedule(task1, 3000);
        
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                prueba.hacerFotosInvisibles();
                prueba.pintarIsla();
            }
        };
        timer.schedule(task2, 6000);
    }
    
    /*
     * Resuelve el problema de maraton encontrando el angulo perfecto
     * 
     * @return float con el angulo correspondiente
     * @throws ANGULO_IMPOSIBLE si no encuentra un angulo que resuelva el problema
     */
    private float resolve(){
        float result = 0;
        String[] islas = prueba.islands();
        prueba.photograph(179);
        String[] islas2 = prueba.observedIslands(); 
        try{
            if(islas.length != islas2.length){
                throw new IceepeeceeException(IceepeeceeException.ANGULO_IMPOSIBLE);
                //result = -1; // aca se podria agregar un excepecion para que se vea mas bonito
            }else{
                prueba.photograph(90);
                islas2 = prueba.observedIslands();
                if(islas.length != islas2.length){
                    float r = this.divisionSup(); // valor minimo 91
                    float r2 = this.divisionFin(r);
                    result = this.divisionFinDec(r2);
                }else{
                    float r = this.divisionInf(); // valor minimo 1
                    float r2 = this.divisionFin(r);
                    result = this.divisionFinDec(r2);
                }
            }
        } catch(IceepeeceeException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    /*
     * Realiza una division en intervalos de 10 desde 179 hasta 91
     * Esto nos ayudara a encontrar el angulo perfecto para el metodo "resolve"
     * 
     * @return float con una aproximación del angulo perfecto
     */
    private float divisionSup(){
        String[] islas = prueba.islands();
        String[] islas2 = prueba.observedIslands();
        float valor = 179;
        int[] valores = {170,160,150,140,130,120,110,100,91};
        for(int i = 0; i < valores.length; i++){
            prueba.photograph(valores[i]);
            if(islas.length == islas2.length){
                valor = valores[i];
            }else{
                break;
            }
        }
        return valor;
    }
    
    /*
     * Realiza una division en intervalos de 10 desde 90 hasta 1
     * Esto nos ayudara a encontrar el angulo perfecto para el metodo "resolve"
     * 
     * @return float con una aproximación del angulo perfecto
     */
    private float divisionInf(){
        String[] islas = prueba.islands();
        String[] islas2 = prueba.observedIslands();
        float valor = 90;
        int[] valores = {80,70,60,50,40,30,20,10,1};
        for(int i = 0; i < valores.length; i++){
            prueba.photograph(valores[i]);
            islas2 = prueba.observedIslands();
            if(islas.length == islas2.length){
                valor = valores[i];
            }else{
                break;
            }
        }
        return valor;
    }
    
    /*
     * Realiza una division en intervalos de 1 
     * Esto nos ayudara a encontrar el angulo perfecto para el metodo "resolve"
     * 
     * @return float con una aproximación del angulo perfecto
     */
    private float divisionFin(float num){
        String[] islas = prueba.islands();
        String[] islas2 = prueba.observedIslands();
        float valor = num;
        float[] valores = {num-1, num-2, num-3, num-4, num-5, num-6, num-7, num-8, num-9};
        for(int i = 0; i < valores.length; i++){
            prueba.photograph(valores[i]);
            islas2 = prueba.observedIslands();
            if(islas.length == islas2.length){
                valor = valores[i];
            }else{
                break;
            }
        }
        return valor;
    }
    
    /*
     * Realiza una division en intervalos de 0.1 
     * Esto nos ayudara a encontrar el angulo perfecto para el metodo "resolve"
     * 
     * @return float con el angulo perfecto
     */
    private float divisionFinDec(float num){
        String[] islas = prueba.islands();
        String[] islas2 = prueba.observedIslands();
        float valor = num;
        float[] valores = {num -0.1f, num -0.2f, num -0.3f, num -0.4f, num -0.5f, num -0.6f, num -0.7f, num -0.8f, num -0.9f};
        for(int i = 0; i < valores.length; i++){
            prueba.photograph(valores[i]);
            islas2 = prueba.observedIslands();
            if(islas.length == islas2.length){
                valor = valores[i];
            }else{
                break;
            }
        }
        return valor;
    }
}
