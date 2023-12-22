package iceepeecee;

import excepciones.IceepeeceeException;

import java.awt.Polygon;

/**
 * Isla que se hace mas pequeña cada vez que se pide su ubicacion.
 *
 * @author Joan Acevedo
 * @version 25/10/2023
 */
public class Surprising extends Island
{
    /**
     * Constructor de la isla tipo Surprising
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @param Iceepeecee, territorio al que pertenece la isla
     * @throws SALE_DEL_TERRITORIO si la isla se sale del territorio
     */
    public Surprising(String color, int[][] polygon, int lengthT, int widthT) throws IceepeeceeException {
        super(color,polygon,lengthT,widthT);
    }
    
    @Override
    public int[][] getLocation(){   
        super.makeInvisible();
        this.location = eliminarUltimoElemento(this.location);
        super.makeVisible();
        return this.location;
    }
    
    /*
     * Elimina el ultimo elemento de la isla Fixed
     * 
     * @param matriz, isla previa al cambio
     * @return int[][] isla con los nuevos cambios
     */
    private int[][] eliminarUltimoElemento(int[][] matriz) {
        if (matriz == null || matriz.length <= 3 || matriz[0].length == 0) {
            return matriz; // Devolver la matriz original si no se puede eliminar el último elemento
        }
        int filas = matriz.length;
        int columnas = matriz[0].length;
        // Crear una nueva matriz con un tamaño menor (una fila menos)
        int[][] nuevaMatriz = new int[filas - 1][columnas];
        // Copiar elementos excepto el último
        for (int i = 0; i < filas - 1; i++) {
            for (int j = 0; j < columnas; j++) {
                nuevaMatriz[i][j] = matriz[i][j];
            }
        }
        this.xPoints = new int[nuevaMatriz.length];
        this.yPoints = new int[nuevaMatriz.length];
        this.nPoints = nuevaMatriz.length;
        for (int i = 0; i < nuevaMatriz.length; i++) {
            this.xPoints[i] = nuevaMatriz[i][0]; 
            this.yPoints[i] = nuevaMatriz[i][1];
        }
        this.form = new Polygon(xPoints, yPoints, nPoints);
        return nuevaMatriz;
    }
}
