package src;

/**
 * Clase Aula. Administra la disponibilidad del espacio mediante una
 * matriz fija nativa y permite su ordenación en estructuras de árbol.
 */
public class Aula implements Comparable<Aula> {
    private String nombre;
    private int capacidad;
    
    // Matriz nativa boolean[7][24] requerida (7 días, 24 horas)
    private boolean[][] mallaHoraria;

    public Aula(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.mallaHoraria = new boolean[7][24]; // Por defecto todo en false (libre)
    }

    /**
     * Intenta reservar un bloque de una hora específica.
     */
    public void reservar(int dia, int hora) throws HorarioConflictivoException {
        if (dia < 0 || dia >= 7 || hora < 0 || hora >= 24) {
            throw new IllegalArgumentException("Día u hora fuera de los rangos académicos.");
        }
        if (mallaHoraria[dia][hora]) {
            throw new HorarioConflictivoException("Conflicto: El aula " + nombre + " ya se encuentra ocupada en ese horario.");
        }
        mallaHoraria[dia][hora] = true;
    }

    public void liberar(int dia, int hora) {
        if (dia >= 0 && dia < 7 && hora >= 0 && hora < 24) {
            mallaHoraria[dia][hora] = false;
        }
    }

    public boolean consultarDisponibilidad(int dia, int hora) {
        if (dia >= 0 && dia < 7 && hora >= 0 && hora < 24) {
            return !mallaHoraria[dia][hora];
        }
        return false;
    }

    /**
     * Criterio de ordenación para el TreeMap solicitado en el Taller.
     */
    @Override
    public int compareTo(Aula otra) {
        int comp = Integer.compare(this.capacidad, otra.capacidad);
        if (comp == 0) {
            return this.nombre.compareTo(otra.nombre);
        }
        return comp;
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public boolean[][] getMallaHoraria() { return mallaHoraria; }
}