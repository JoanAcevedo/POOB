package iceepeecee;

import excepciones.IceepeeceeException;

/**
 * Vuelo que solo toma una foto.
 *
 * @author Joan Acevedo
 * @version 25/10/2023
 */
public class Lazy extends Fligth
{
    private boolean photographed;
    
    /**
     * Constructor del vuelo tipo Lazy
     * 
     * @param color, color del vuelo
     * @param from, punto de inicio del vuelo
     * @param to, punto de llegada del vuelo
     * @param Iceepeecee, territorio al que pertenece el vuelo
     * @throws SALE_DEL_TERRITORIO si el vuelo se sale del territorio
     */
    public Lazy(String color, int[] from, int[] to, int lengthT, int widthT) throws IceepeeceeException{
        super(color,from,to,lengthT,widthT);
        photographed = false;
    }
    
    @Override
    public void makePhotograph(float teta) throws IceepeeceeException{
        if(!photographed){
            super.makePhotograph(teta);
            photographed = true;
        }else{
            throw new IceepeeceeException(IceepeeceeException.VUELO_LAZY);
        }
    }
}
