package src;

/**
 * Excepción cuando se intenta reservar un aula en una hora ya ocupada.
 */
public class HorarioConflictivoException extends Exception {
    public HorarioConflictivoException(String mensaje) {
        super(mensaje);
    }
}