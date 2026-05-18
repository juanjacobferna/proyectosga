package src;

/**
 * Excepción cuando una materia no tiene más espacios disponibles.
 */
public class CupoLlenoException extends Exception {
    public CupoLlenoException(String mensaje) {
        super(mensaje);
    }
}