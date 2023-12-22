package excepciones;

/**
 * Clase encargada de generar excepciones.
 * 
 * @author Joan Acevedo
 * @version 29/10/2023
 */
public class IceepeeceeException extends Exception
{
    public static final String TERRITORIO_IMPOSIBLE = "Valores incorrectos, los rangos de valores son (1 a 700) en 'X' y 'Y'";
    public static final String SALE_DEL_TERRITORIO = "Las coordenadas son incorrectas, ingresa nuevas coordenadas";
    public static final String TIPO_INEXISTENTE_ISLA = "Ingresaste un tipo inexistente de isla, los tipos son: 'n', 's', 'f'";
    public static final String TIPO_INEXISTENTE_VUELO = "Ingresaste un tipo inexistente de vuelo, los tipos son: 'n', 'l', 'f'";
    public static final String ISLA_FIXED = "Las islas tipo Fixed no pueden ser eliminadas";
    public static final String VUELO_LAZY = "Este vuelo ya no toma mas fotos por perezoso";
    public static final String ANGULO_IMPOSIBLE = "No existe un angulo que cumpla las condiciones";
    public static final String ISLA_REPETIDA = "La isla ya existe, elige otro color";
    public static final String VUELO_REPETIDO = "El vuelo ya existe, elige otro color";
    public static final String ANGULO_INCORRECTO = "Valores incorrectos, los rangos de valores son (1 a 180)";
    public static final String ISLA_NULL = "La isla ingresada no existe";
    public static final String VUELO_NULL = "El vuelo ingresada no existe";
    public static final String FOTO_NULL = "Este vuelo no cuenta con una foto";
    
    public IceepeeceeException(String message){
        super(message);
    }
}
