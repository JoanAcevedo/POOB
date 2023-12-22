package domain;


/**
 * Esta es la clase encargada de generar excepciones.
 *
 * @author Joan Acevedo / Santiago Sanchez
 * @version 27/10/2023
 */
public class CostumeShopException extends Exception
{
    public static final String PRICE_EMPTY = "Precio vacio";
    public static final String PRICE_ERROR = "Precio incorrecto";
    public static final String PRICE_UNKNOWN = "Precio desconocido";
    public static final String COMPLETE_EMPTY = "No se tienen disfraces basicos";
    public static final String IMPOSSIBLE = "No se puede calcular el precio estimado";
    
    public CostumeShopException(String message){
        super(message);
    }
}
