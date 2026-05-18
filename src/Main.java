package src;

import java.util.Scanner;

/**
 * Clase Principal (Vista - V del patrón MVC).
 * Despliega el menú interactivo para el usuario en la terminal.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SistemaGCA sistema = new SistemaGCA();
        
        // Pila para el sistema de control de historial de navegación/deshacer
        Pila<String> historialAcciones = new Pila<>();

        // Intentar cargar datos guardados previamente desde el CSV de forma automática
        sistema.cargarDatosCSV("datos_academicos.csv");
        
        // Datos de prueba iniciales si el sistema arranca vacío
        inicializarDatosPorDefecto(sistema);

        int opcion = 0;
        do {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE GESTIÓN ACADÉMICA (SGA)");
            System.out.println("========================================");
            System.out.println("1. Registrar nuevo Estudiante");
            System.out.println("2. Consultar información de un Estudiante");
            System.out.println("3. Inscribir Materia a un Estudiante");
            System.out.println("4. Consultar disponibilidad de un Aula");
            System.out.println("5. Calcular Ruta Óptima en el Campus (Dijkstra)");
            System.out.println("6. Ver Historial de Últimas Acciones (Pila)");
            System.out.println("7. Guardar y Respaldar Datos (CSV)");
            System.out.println("8. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                
                switch (opcion) {
                    case 1:
                        System.out.print("Ingrese ID del estudiante: ");
                        String id = scanner.nextLine();
                        System.out.print("Ingrese Nombre completo: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Ingrese Email institucional: ");
                        String email = scanner.nextLine();
                        
                        Estudiante nuevoEst = new Estudiante(nombre, id, email, 1);
                        sistema.registrarEstudiante(nuevoEst);
                        historialAcciones.empujar("Registró al estudiante: " + nombre);
                        System.out.println("¡Estudiante registrado con éxito!");
                        break;
                        
                    case 2:
                        System.out.print("Ingrese el ID del estudiante a consultar: ");
                        String idBusqueda = scanner.nextLine();
                        Estudiante est = sistema.buscarEstudiante(idBusqueda);
                        if (est != null) {
                           est.mostrarInformacion(); // Llama al método polimórfico
                        } else {
                            System.out.println("Error: Estudiante no encontrado.");
                        }
                        break;
                        
                    case 3:
                        System.out.print("Ingrese el ID del estudiante: ");
                        String idEstMat = scanner.nextLine();
                        System.out.print("Ingrese el código de la materia (e.g., MAT101, INF202): ");
                        String codMat = scanner.nextLine();
                        
                        Estudiante alumno = sistema.buscarEstudiante(idEstMat);
                        Materia asignatura = sistema.buscarMateria(codMat);
                        
                        if (alumno == null) {
                            System.out.println("Error: El estudiante no existe.");
                        } else if (asignatura == null) {
                            System.out.println("Error: La materia no existe.");
                        } else {
                            // Aplicamos la lógica de cupos y colas manuales
                            if (asignatura.tieneCupo()) {
                                asignatura.reducirCupo();
                                alumno.getHistorialMaterias().agregarAlFinal(asignatura.getCodigo());
                                // Registramos una nota inicial por defecto (0.0) en su matriz
                                int pos = alumno.getHistorialMaterias().getTamanio() - 1;
                                alumno.registrarNotaMatriz(0, pos, 0.0);
                                System.out.println("¡Inscripción exitosa en " + asignatura.getNombre() + "!");
                                historialAcciones.empujar("Inscribió a " + alumno.getNombre() + " en " + asignatura.getNombre());
                            } else {
                                System.out.println("¡Cupo Lleno! Agregando al estudiante a la cola de espera de la materia...");
                                asignatura.getColaEspera().encolar(alumno.getId());
                                System.out.println("Estudiante en cola de espera. Posición actual: " + asignatura.getColaEspera().getTamanio());
                            }
                        }
                        break;
                        
                    case 4:
                        System.out.print("Ingrese el nombre del Aula (e.g., Aula 101, Lab 202): ");
                        String nombreAula = scanner.nextLine();
                        Aula aula = sistema.getMapaAulas().get(nombreAula);
                        
                        if (aula != null) {
                            System.out.println("Aula: " + aula.getNombre() + " | Capacidad: " + aula.getCapacidad() + " personas.");
                            System.out.println("Disponibilidad Lunes 8:00 AM: " + (aula.consultarDisponibilidad(0, 8) ? "LIBRE" : "OCUPADA"));
                        } else {
                            System.out.println("El aula especificada no está registrada en el TreeMap.");
                        }
                        break;
                        
                    case 5:
                        String[] edificios = sistema.getGestorRutas().getNombresEdificios();
                        System.out.println("\n--- Edificios del Campus ---");
                        for (int i = 0; i < edificios.length; i++) {
                            System.out.println(i + ". " + edificios[i]);
                        }
                        System.out.print("Seleccione el número del punto de ORIGEN: ");
                        int orig = Integer.parseInt(scanner.nextLine());
                        System.out.print("Seleccione el número del punto de DESTINO: ");
                        int dest = Integer.parseInt(scanner.nextLine());
                        
                        sistema.getGestorRutas().calcularRutaOptima(orig, dest);
                        break;
                        
                    case 6:
                        System.out.println("\n--- Historial de Operaciones Recientes (Pila LIFO) ---");
                        if (historialAcciones.estaVacia()) {
                            System.out.println("No se han realizado transacciones en esta sesión.");
                        } else {
                            // Mostramos lo que hay en la cima de la pila sin destruirla completamente
                            System.out.println("Última acción guardada en memoria: " + historialAcciones.mirarCima());
                            System.out.println("Total de acciones rastreadas: " + historialAcciones.getTamanio());
                        }
                        break;
                        
                    case 7:
                        sistema.guardarDatosCSV("datos_academicos.csv");
                        break;
                        
                    case 8:
                        System.out.println("Salvando estado del sistema... ¡Gracias por usar el SGA!");
                        sistema.guardarDatosCSV("datos_academicos.csv");
                        break;
                        
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (Exception ex) {
                System.out.println("Error en la entrada: " + ex.getMessage());
            }
        } while (opcion != 8);
        
        scanner.close();
    }

    /**
     * Llena el sistema con datos iniciales para asegurar que funcione desde la primera corrida.
     */
   private static void inicializarDatosPorDefecto(SistemaGCA sistema) {
    // Registrar materias base para que el sistema tenga donde matricular
    sistema.registrarMateria(new Materia("INF202", "Estructuras de Datos", 3, 4));
    sistema.registrarMateria(new Materia("INF101", "Introducción a la Programación", 2, 3));
    sistema.registrarMateria(new Materia("MAT101", "Cálculo Diferencial", 1, 4));

    // Registrar aulas base de prueba
    sistema.registrarAula(new Aula("Aula 101", 30));
    sistema.registrarAula(new Aula("Lab Informática B", 15));

    // --- INYECCIÓN DIRECTA DE LOS 20 ESTUDIANTES REQUERIDOS ---
    sistema.registrarEstudiante(new Estudiante("Juan Jacob Lopez Fernández", "2124242", "juan@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Carlos Andrés Pérez Ospina", "1005123", "carlos@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("María Camila Torres Buendía", "1005456", "maria@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Andrés Felipe Beltrán Ruiz", "1005789", "andres@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Diana Marcela Gómez Castro", "1006111", "diana@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Santiago Alejandro Muñoz Marín", "1006222", "santiago@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Valentina Sofía Erazo Córdoba", "1006333", "valentina@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Mateo Nicolás Delgado Ortiz", "1006444", "mateo@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Isabella Rose Gómez Hurtado", "1006555", "isabella@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Juan Diego Palacios Vivas", "1006666", "juand@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Mariana Lucía Rojas Benavides", "1006777", "mariana@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Kevin Alberto Restrepo Solano", "1006888", "kevin@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Laura Daniela Caicedo Peña", "1006999", "laura@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Sebastián Camilo Murillo Paz", "1007112", "sebastian@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Valeria Alexandra Mina Castillo", "1007223", "valeria@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Daniel Fernando Angulo Tenorio", "1007334", "daniel@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Natalia María Suárez Vargas", "1007445", "natalia@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Alejandro José Borrero Navia", "1007556", "alejandro@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Gabriela Estefanía Cifuentes Saa", "1007667", "gabriela@u.edu.co", 1));
    sistema.registrarEstudiante(new Estudiante("Jhan Carlos Hinestroza Mosquera", "1007778", "jhan@u.edu.co", 1));
}

}


