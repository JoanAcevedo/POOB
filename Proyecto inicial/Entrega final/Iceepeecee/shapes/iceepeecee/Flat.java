package iceepeecee;

import excepciones.IceepeeceeException;

/**
 * Write a description of class Flat here.
 *
 * @author Joan Acevedo
 * @version 25/10/2023
 */
public class Flat extends Fligth
{
    /**
     * Constructor del vuelo tipo Flat
     * 
     * @param color, color del vuelo
     * @param from, punto de inicio del vuelo
     * @param to, punto de llegada del vuelo
     * @param Iceepeecee, territorio al que pertenece el vuelo
     * @throws SALE_DEL_TERRITORIO si el vuelo se sale del territorio
     */
    public Flat(String color, int[] from, int[] to, int lengthT, int widthT) throws IceepeeceeException{
        super(color,from,to,lengthT,widthT);
        to[2]=from[2];
    }
    
}
