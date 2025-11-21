package primerparcial;

import primerparcial.excepciones.OperacionNoImplementadaException;

public class Concepto {
    private static int contadorId = 0;
    protected int id;
    protected String descripcion;

    public Concepto(String descripcion) {
        this.id = ++contadorId;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double obtenerImporte(double valorBaseCalculo){
        throw new OperacionNoImplementadaException();
    };
}
