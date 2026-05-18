package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Controlador Central (C del modelo MVC).
 * Administra el estado global del sistema, colecciones y persistencia CSV.
 */
public class SistemaGCA {
    // Estructuras de Java exigidas en el Punto 2 del Taller
    private HashMap<String, Estudiante> mapaEstudiantes;
    private TreeMap<String, Aula> mapaAulas;
    private ListaEnlazada<Materia> listaMaterias;
    
    // Motor de rutas del campus (Grafo)
    private GestorRutas gestorRutas;

    public SistemaGCA() {
        this.mapaEstudiantes = new HashMap<>();
        this.mapaAulas = new TreeMap<>();
        this.listaMaterias = new ListaEnlazada<>();
        this.gestorRutas = new GestorRutas();
    }

    // ==========================================
    //   PUNTO 4.1: PERSISTENCIA DE DATOS (CSV)
    // ==========================================
    
    /**
     * Exporta el historial académico de los estudiantes a un archivo CSV.
     */
    public void guardarDatosCSV(String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            // Cabecera del archivo para que sea legible en Excel
            writer.println("ID_Estudiante,Nombre,Materia_Codigo,Nota");
            
            // Recorremos el HashMap de estudiantes
            for (Estudiante est : mapaEstudiantes.values()) {
                Double[][] notas = est.getNotasPorSemestre();
                ListaEnlazada<String> historial = est.getHistorialMaterias();
                
                // Guardamos la información de las materias que tiene registradas
                for (int i = 0; i < historial.getTamanio(); i++) {
                    String codMateria = historial.obtener(i);
                    // Simulamos la persistencia de su nota en el semestre 0 por simplicidad
                    Double nota = notas[0][i] != null ? notas[0][i] : 0.0;
                    writer.println(est.getId() + "," + est.getNombre() + "," + codMateria + "," + nota);
                }
            }
            System.out.println("Datos respaldados exitosamente en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error crítico al exportar a CSV: " + e.getMessage());
        }
    }

    /**
     * Importa los datos desde el archivo CSV reconstruyendo el estado del sistema.
     */
    public void cargarDatosCSV(String nombreArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignorar cabecera
                    primeraLinea = false;
                     Kaliman: continue;
                }
                
                String[] datos = linea.split(",");
                if (datos.length >= 4) {
                    String id = datos[0];
                    String nombre = datos[1];
                    String codMateria = datos[2];
                    double nota = Double.parseDouble(datos[3]);
                    
                    // Si el estudiante no existe en el HashMap, lo creamos
                    if (!mapaEstudiantes.containsKey(id)) {
                        Estudiante nuevo = new Estudiante(nombre, id, id + "@universidad.edu.co", 1);
                        mapaEstudiantes.put(id, nuevo);
                    }
                    
                    // Reconstruimos su historial y su matriz de notas
                    Estudiante est = mapaEstudiantes.get(id);
                    if (!est.getHistorialMaterias().contiene(codMateria)) {
                        est.getHistorialMaterias().agregarAlFinal(codMateria);
                        int indice = est.getHistorialMaterias().getTamanio() - 1;
                        est.registrarNotaMatriz(0, indice, nota);
                    }
                }
            }
            System.out.println("Persistencia cargada con éxito desde el CSV.");
        } catch (IOException e) {
            System.out.println("Aviso: No se encontró archivo de persistencia previo. Iniciando sistema limpio.");
        }
    }

    // ==========================================
    //        LÓGICA INTERNA DE MATRÍCULAS
    // ==========================================

    public void registrarEstudiante(Estudiante e) {
        mapaEstudiantes.put(e.getId(), e);
    }

    public void registrarAula(Aula a) {
        mapaAulas.put(a.getNombre(), a);
    }

    public void registrarMateria(Materia m) {
        listaMaterias.agregarAlFinal(m);
    }

    public Materia buscarMateria(String codigo) {
        for (int i = 0; i < listaMaterias.getTamanio(); i++) {
            Materia m = listaMaterias.obtener(i);
            if (m.getCodigo().equalsIgnoreCase(codigo)) return m;
        }
        return null;
    }

    public Estudiante buscarEstudiante(String id) {
        return mapaEstudiantes.get(id);
    }

    public GestorRutas getGestorRutas() { return gestorRutas; }
    public HashMap<String, Estudiante> getMapaEstudiantes() { return mapaEstudiantes; }
    public TreeMap<String, Aula> getMapaAulas() { return mapaAulas; }
}