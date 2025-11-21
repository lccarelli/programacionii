package archisolutions.app;

import archisolutions.app.excepciones.OperacionInvalidaException;

import java.util.ArrayList;

public class Alumno extends Persona{
    private int legajo;
    private ArrayList<Nota> notas = new ArrayList<>();

    public Alumno(String nombre, int dni, int edad) {
        super(nombre, dni, edad);
    }

    @Override
    public double calcularSueldoFinal() {
        throw new OperacionInvalidaException();
    }

    @Override
    public boolean requiereAtencion() {
        return calcularPromedio()<4;
    }

    public void agregarNota(Materia materia, double nota){
        notas.add(new Nota(materia, nota));
    }

    public double calcularPromedio(){
        double promedio = 0;
        for (Nota nota : notas){
            promedio += nota.getValor();
        }
        return promedio /= notas.size();
    }

}
