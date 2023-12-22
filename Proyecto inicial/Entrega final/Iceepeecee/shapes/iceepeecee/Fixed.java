package iceepeecee;

import excepciones.IceepeeceeException;

/**
 * Isla que no se deja eliminar.
 *
 * @author Joan Acevedo
 * @version 25/20/2023
 */
public class Fixed extends Island 
{
    /**
     * Constructor de la isla tipo Fixed
     * 
     * @param color, color de la isla
     * @param polygon, puntos con coordenadas de la isla
     * @param Iceepeecee, territorio al que pertenece la isla
     * @throws SALE_DEL_TERRITORIO si la isla se sale del territorio
     */
    public Fixed(String color, int[][] polygon, int lengthT, int widthT) throws IceepeeceeException {
        super(color,polygon,lengthT,widthT);
    }    
}
