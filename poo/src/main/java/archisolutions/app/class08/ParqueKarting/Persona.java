package archisolutions.app.class08.ParqueKarting;

import java.util.Objects;

public class Persona {
    private String nombre;
    private String apellido;
    private int edad;
    private static int EDAD_MINIMA = 2;
    private static int EDAD_MAXIMA = 65;

    public Persona(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean edadPermitida() {
        return edad >= EDAD_MINIMA && edad <= EDAD_MAXIMA;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona persona)) return false;
        return edad == persona.edad && Objects.equals(nombre, persona.nombre) && Objects.equals(apellido, persona.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, edad);
    }
}
