package domain;

/**
 * Custom exception class for GomokuPOOs related exceptions.
 * Extends the base Exception class in Java.
 */
public class ExcepcionGomokuPOOs extends Exception {
    /**
     * Constructs a new instance of the ExcepcionGomokuPOOs class with the specified detail message.
     *
     * @param mensaje The detail message for the exception. This message provides additional information
     *                about the exception and can be retrieved using the getMessage() method of the base Exception class.
     */
    public ExcepcionGomokuPOOs(String mensaje) {
        super(mensaje);
    }
}
