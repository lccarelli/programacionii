package primerparcial;

public class RemuneracionVariable extends Concepto {
    private double porcentaje;

    public RemuneracionVariable(String descripcion, double porcentaje) {
        super(descripcion);
        this.porcentaje = porcentaje;
    }

    @Override
    public double obtenerImporte(double sueldoBasico) {
        return sueldoBasico * (this.porcentaje / 100.0);
    }
}