package src;

/**
 * Implementación de una Cola Dinámica Genérica (FIFO).
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int tamanio;

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamanio = 0;
    }

    public void encolar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        if (estaVacia()) {
            frente = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
        }
        fin = nuevoNodo;
        tamanio++;
    }

    public T desencolar() throws Exception {
        if (estaVacia()) {
            throw new Exception("La cola está vacía.");
        }
        T contenido = frente.getContenido();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null;
        }
        tamanio--;
        return contenido;
    }

    public T mirarFrente() throws Exception {
        if (estaVacia()) {
            throw new Exception("La cola está vacía.");
        }
        return frente.getContenido();
    }

    public int getTamanio() { return tamanio; }
    public boolean estaVacia() { return frente == null; }
}