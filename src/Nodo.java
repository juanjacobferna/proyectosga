package src;

/**
 * Clase Nodo Genérica. Al usar 'package src;', se comunica
 * automáticamente con el resto del proyecto sin necesidad de imports.
 */
public class Nodo<T> {
    private T contenido;
    private Nodo<T> siguiente;

    public Nodo(T contenido) {
        this.contenido = contenido;
        this.siguiente = null;
    }

    public T getContenido() { return contenido; }
    public void setContenido(T contenido) { this.contenido = contenido; }
    public Nodo<T> getSiguiente() { return siguiente; }
    public void setSiguiente(Nodo<T> siguiente) { this.siguiente = siguiente; }
}