package src;

/**
 * Clase Estudiante. Aplica herencia y encapsula el control de notas e historial.
 */
public class Estudiante extends Persona {
    private int semestreActual;
    
    // Matriz nativa Double[10][20] obligatoria (10 semestres, 20 materias max por semestre)
    private Double[][] notasPorSemestre;
    
    // Lista enlazada propia para almacenar los códigos de las materias cursadas/aprobadas
    private ListaEnlazada<String> historialMaterias;

    public Estudiante(String nombre, String id, String email, int semestreActual) {
        super(nombre, id, email);
        this.semestreActual = semestreActual;
        this.notasPorSemestre = new Double[10][20];
        this.historialMaterias = new ListaEnlazada<>();
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("=== DATOS ESTUDIANTE ===");
        System.out.println("ID: " + getId());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Email: " + getEmail());
        System.out.println("Semestre Actual: " + semestreActual);
        System.out.println("Materias cursadas: " + historialMaterias.getTamanio());
        System.out.println("========================");
    }

    /**
     * Registra una calificación en una celda específica de la matriz nativa.
     */
    public void registrarNotaMatriz(int semestre, int indiceMateria, double nota) {
        if (semestre >= 0 && semestre < 10 && indiceMateria >= 0 && indiceMateria < 20) {
            this.notasPorSemestre[semestre][indiceMateria] = nota;
        }
    }

    // Getters y Setters
    public int getSemestreActual() { return semestreActual; }
    public void setSemestreActual(int semestreActual) { this.semestreActual = semestreActual; }
    public Double[][] getNotasPorSemestre() { return notasPorSemestre; }
    public ListaEnlazada<String> getHistorialMaterias() { return historialMaterias; }
}