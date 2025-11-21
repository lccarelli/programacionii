package archisolutions.app;

public class Profesor extends Persona{
    private Materia materia;
    private double sueldoBase;

    public Profesor(String nombre, int dni, int edad, Materia materia, double sueldoBase) {
        super(nombre, dni, edad);
        this.materia = materia;
        this.sueldoBase = sueldoBase;
    }

    @Override
    public double calcularSueldoFinal() {
        return sueldoBase + (sueldoBase*materia.getBono());
    }

    @Override
    public boolean requiereAtencion() {
        return super.getEdad()>65;
    }

    public Materia getMateria(){
        return materia;
    }
}
