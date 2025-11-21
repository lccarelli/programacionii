package archisolutions.app;

public class Administrativo extends Persona{
    private int horasExtras;
    private double sueldoBase;

    public Administrativo(String nombre, int dni, int edad, double sueldoBase) {
        super(nombre, dni, edad);
        this.sueldoBase = sueldoBase;
    }

    @Override
    public double calcularSueldoFinal() {
        return sueldoBase + (horasExtras*500);
    }

    @Override
    public boolean requiereAtencion() {
        return horasExtras>40;
    }

    public int getHorasExtras(){
        return horasExtras;
    }

    public void setSueldoBase(double sueldoBase){
        this.sueldoBase = sueldoBase;
    }

    public void cargarHorasExtras(int horasExtras){
        this.horasExtras += horasExtras;
    }
}
