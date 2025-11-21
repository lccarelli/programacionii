package archisolutions.app;

import archisolutions.app.excepciones.MetodoNoImplementadoException;

public class Persona {
    private static int ultimoId = 0;
    private int id;
    private String nombre;
    private int dni;
    private int edad;

    public Persona(String nombre, int dni, int edad) {
        this.id = ++ultimoId;
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
    }

    public int getId() {
        return id;
    }

    public int getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public double calcularSueldoFinal(){
        throw new MetodoNoImplementadoException();
    }

    public boolean requiereAtencion(){
        throw new MetodoNoImplementadoException();
    }
}
