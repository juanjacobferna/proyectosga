package src;

/**
 * Clase que gestiona el mapa del campus mediante un Grafo.
 * Implementa el algoritmo de Dijkstra con matrices nativas fijas.
 */
public class GestorRutas {
    private int numEdificios;
    private String[] nombresEdificios;
    
    // Matriz de adyacencia nativa int[N][N] obligatoria para las distancias
    private int[][] matrizAdyacencia;
    
    // Representación de infinito para caminos no conectados
    private static final int INF = 999999;

    public GestorRutas() {
        this.numEdificios = 5;
        this.nombresEdificios = new String[]{
            "Edificio A (Aulas Generales)", 
            "Edificio B (Laboratorios)", 
            "Edificio C (Biblioteca Central)", 
            "Edificio D (Bloque Administrativo)", 
            "Edificio E (Cafetería Principal)"
        };
        this.matrizAdyacencia = new int[numEdificios][numEdificios];
        inicializarMapa();
    }

    /**
     * Define las conexiones y distancias fijas en metros dentro del campus.
     */
    private void inicializarMapa() {
        // Llenar la matriz con INF y la diagonal con 0
        for (int i = 0; i < numEdificios; i++) {
            for (int j = 0; j < numEdificios; j++) {
                if (i == j) matrizAdyacencia[i][j] = 0;
                else matrizAdyacencia[i][j] = INF;
            }
        }
        
        // Configurar distancias reales entre los nodos (Grafo No Dirigido)
        conectar(0, 1, 50);  // Edificio A <-> Edificio B (50 metros)
        conectar(0, 2, 120); // Edificio A <-> Edificio C (120 metros)
        conectar(1, 2, 60);  // Edificio B <-> Edificio C (60 metros)
        conectar(1, 3, 200); // Edificio B <-> Edificio D (200 metros)
        conectar(2, 4, 90);  // Edificio C <-> Edificio E (90 metros)
        conectar(3, 4, 40);  // Edificio D <-> Edificio E (40 metros)
    }

    private void conectar(int i, int j, int distancia) {
        matrizAdyacencia[i][j] = distancia;
        matrizAdyacencia[j][i] = distancia; // Doble vía
    }

    /**
     * Aplica el algoritmo de Dijkstra para hallar e imprimir la ruta óptima.
     */
    public void calcularRutaOptima(int origen, int destino) {
        if (origen < 0 || origen >= numEdificios || destino < 0 || destino >= numEdificios) {
            System.out.println("Error: Edificios seleccionados fuera de rango.");
            return;
        }

        int[] distancias = new int[numEdificios];
        boolean[] visitados = new boolean[numEdificios];
        int[] predecesores = new int[numEdificios]; // Para reconstruir el camino exacto

        for (int i = 0; i < numEdificios; i++) {
            distancias[i] = INF;
            visitados[i] = false;
            predecesores[i] = -1;
        }

        distancias[origen] = 0;

        for (int count = 0; count < numEdificios - 1; count++) {
            int u = evaluarMinimaDistancia(distancias, visitados);
            if (u == -1) break;
            
            visitados[u] = true;

            for (int v = 0; v < numEdificios; v++) {
                if (!visitados[v] && matrizAdyacencia[u][v] != INF && 
                    distancias[u] != INF && distancias[u] + matrizAdyacencia[u][v] < distancias[v]) {
                    distancias[v] = distancias[u] + matrizAdyacencia[u][v];
                    predecesores[v] = u;
                }
            }
        }

        // Imprimir el reporte detallado que pide la rúbrica del taller
        if (distancias[destino] == INF) {
            System.out.println("No se encontró una ruta transitable entre los edificios.");
        } else {
            System.out.println("\n========================================");
            System.out.println("       ANÁLISIS DE RUTA ÓPTIMA");
            System.out.println("========================================");
            System.out.println("Distancia Total a recorrer: " + distancias[destino] + " metros.");
            System.out.print("Ruta sugerida: ");
            imprimirCaminoRecursivo(destino, predecesores);
            System.out.println("\n========================================");
        }
    }

    private int evaluarMinimaDistancia(int[] distancias, boolean[] visitados) {
        int min = INF, minIndex = -1;
        for (int v = 0; v < numEdificios; v++) {
            if (!visitados[v] && distancias[v] <= min) {
                min = distancias[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private void imprimirCaminoRecursivo(int nodoActual, int[] predecesores) {
        if (predecesores[nodoActual] == -1) {
            System.out.print(nombresEdificios[nodoActual]);
            return;
        }
        imprimirCaminoRecursivo(predecesores[nodoActual], predecesores);
        System.out.print(" -> " + nombresEdificios[nodoActual]);
    }

    // Getters para el menú interactivo
    public String[] getNombresEdificios() { return nombresEdificios; }
}