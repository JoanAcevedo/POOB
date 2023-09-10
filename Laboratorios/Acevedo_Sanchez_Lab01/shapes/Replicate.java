import java.util.Random;

/**
 * Matriz de Hexagonos que ofrece distintos metodos.
 * 
 * @author Joan Acevedo / Santiago Sanchez 
 * @version 02/09/202
 */
public class Replicate{
    
    private Hexagon[][] celdas;
    private int dimension;
    private int xPosition = -18;
    private int yPosition = -18;
    private Random random = new Random();
    
    /**
     * Constructor de Replicate
     * @param dimen, dimension de Replicate
     */
    public Replicate(int dimension){
        this.dimension = dimension;
        celdas = new Hexagon[dimension][dimension];
        creador();
    }
    
    /**
     * Crea una pieza en una poscion determinada de Replicate
     * @param iPosition, posicion en i de la pieza
     * @param jPosition, posicion en j de la pieza
     */
    public void pieza(int iPosition, int jPosition){
        for(int i = 0; i < dimension; i ++){
            for(int j = 0; j < dimension; j++){
                celdas[iPosition][jPosition].changeColor("green");
                celdas[iPosition][jPosition].makeVisible();
                break;
            }
        }
    }
    
    /**
     * Crea una pieza en una posicion aleatoria de Replicate
     */
    public void pieza(){
        int iValue;
        int jValue;
        iValue = random.nextInt(this.dimension);
        jValue = random.nextInt(this.dimension);
        for(int i = 0; i < dimension; i ++){
            for(int j = 0; j < dimension; j++){
                celdas[iValue][jValue].changeColor("green");
                celdas[iValue][jValue].makeVisible();
            }
        }
    }
    
    /**
     * Cumple parcialmente la funcion de Replicate
     */
    public void replicar(){
        int cont = 0;
        for(int i = 0; i < dimension -1; i ++){
            for(int j = 0; j < dimension -1; j++){
                if(cont == 0){
                    if(celdas[i][j].getColor() == "green"){
                        if(celdas[i-1][j-1].getColor() == "red"){
                            celdas[i-1][j-1].changeColor("green");
                        }if(celdas[i][j-1].getColor() == "red"){
                            celdas[i][j-1].changeColor("green");
                        }if(celdas[i+1][j-1].getColor() == "red"){
                            celdas[i+1][j-1].changeColor("green");
                        }if(celdas[i-1][j].getColor() == "red"){
                            celdas[i-1][j].changeColor("green");
                        }if(celdas[i+1][j].getColor() == "red"){
                            celdas[i+1][j].changeColor("green");
                        }if(celdas[i-1][j+1].getColor() == "red"){
                            celdas[i-1][j+1].changeColor("green");
                        }if(celdas[i][j+1].getColor() == "red"){
                            celdas[i][j+1].changeColor("green");
                        }if(celdas[i+1][j+1].getColor() == "red"){
                            celdas[i+1][j+1].changeColor("green");
                        }
                        celdas[i][j].changeColor("red");   
                        cont += 1;
                    }
                }
            }
        }
        makeVisible();
    }
    
    /**
     * Hace visible a Replicate
     */
    public void makeVisible(){
        for(int i = 0; i < dimension; i ++){
            for(int j = 0; j < dimension; j++){
                celdas[i][j].makeVisible();
            }
        }
    }
    
    /**
     * Hace invisible a Replicate
     */
    public void makeInvisible(){
        for(int i = 0; i < dimension; i ++){
            for(int j = 0; j < dimension; j++){
                celdas[i][j].makeInvisible();
            }
        }
    }
    
    /*
     * Crea la estructura de Replicate
     */
    private void creador(){
        for(int i = 0; i < dimension; i ++){
            this.yPosition += 38;
            this.xPosition = -18;
            for(int j = 0; j < dimension; j++){
                celdas[i][j] = new Hexagon();
                celdas[i][j].changeSize(20);
                this.xPosition += 40;
                celdas[i][j].changePosition(xPosition, yPosition);
            }
        }
    }
    
}
