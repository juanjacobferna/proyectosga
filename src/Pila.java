package src;

/**
 * Implementación de una Pila Dinámica Genérica (LIFO).
 */
public class Pila<T> {
    private Nodo<T> cima;
    private int tamanio;

    public Pila() {
        this.cima = null;
        this.tamanio = 0;
    }

    public void empujar(T elemento) {
        Nodo<T> nuevoNodo = new Nodo<>(elemento);
        nuevoNodo.setSiguiente(cima);
        cima = nuevoNodo;
        tamanio++;
    }

    public T sacar() throws Exception {
        if (estaVacia()) {
            throw new Exception("La pila está vacía. No hay estados para deshacer.");
        }
        T contenido = cima.getContenido();
        cima = cima.getSiguiente();
        tamanio--;
        return contenido;
    }

    public T mirarCima() throws Exception {
        if (estaVacia()) {
            throw new Exception("La pila está vacía.");
        }
        return cima.getContenido();
    }

    public int getTamanio() { return tamanio; }
    public boolean estaVacia() { return cima == null; }
}