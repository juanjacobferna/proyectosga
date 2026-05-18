package src;

/**
 * Clase Materia. Gestiona la información de la asignatura,
 * validación de prerrequisitos y su respectiva lista de espera.
 */
public class Materia {
    private String codigo;
    private String nombre;
    private int cuposDisponibles;
    private int creditos;
    
    // Lista enlazada propia con los códigos de las materias prerrequisito
    private ListaEnlazada<String> preRequisitos;
    
    // Cola propia para almacenar los IDs de los estudiantes en espera
    private Cola<String> colaEspera;

    public Materia(String codigo, String nombre, int cupos, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cuposDisponibles = cupos;
        this.creditos = creditos;
        this.preRequisitos = new ListaEnlazada<>();
        this.colaEspera = new Cola<>();
    }

    public void agregarPreRequisito(String codigoMateria) {
        if (!this.preRequisitos.contiene(codigoMateria)) {
            this.preRequisitos.agregarAlFinal(codigoMateria);
        }
    }

    public boolean tieneCupo() {
        return cuposDisponibles > 0;
    }

    public void reducirCupo() {
        if (cuposDisponibles > 0) cuposDisponibles--;
    }

    public void liberarCupo() {
        cuposDisponibles++;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getCuposDisponibles() { return cuposDisponibles; }
    public int getCreditos() { return creditos; }
    public ListaEnlazada<String> getPreRequisitos() { return preRequisitos; }
    public Cola<String> getColaEspera() { return colaEspera; }
}