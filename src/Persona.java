package src;

/**
 * Clase abstracta Persona. Define el contrato base y los atributos
 * comunes de cualquier usuario del sistema.
 */
public abstract class Persona {
    private String nombre;
    private String id;
    private String email;

    public Persona(String nombre, String id, String email) {
        this.nombre = nombre;
        this.id = id;
        this.email = email;
    }

    /**
     * Método abstracto polimórfico para ser implementado por las clases hijas.
     */
    public abstract void mostrarInformacion();

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}