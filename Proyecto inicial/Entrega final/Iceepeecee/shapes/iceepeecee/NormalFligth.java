package iceepeecee;

import excepciones.IceepeeceeException;

/**
 * Write a description of class NormalFligth here.
 * 
 * @author Joan Acevedo 
 * @version 04/11/2023
 */
public class NormalFligth extends Fligth
{
    public NormalFligth(String color, int[] from, int[] to, int lengthT, int widthT) throws IceepeeceeException{
        super(color,from,to,lengthT,widthT);
    }
}
