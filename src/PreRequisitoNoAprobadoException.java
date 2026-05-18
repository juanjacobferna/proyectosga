package src;

/**
 * Excepción cuando un estudiante no cumple con las materias previas obligatorias.
 */
public class PreRequisitoNoAprobadoException extends Exception {
    public PreRequisitoNoAprobadoException(String mensaje) {
        super(mensaje);
    }
}