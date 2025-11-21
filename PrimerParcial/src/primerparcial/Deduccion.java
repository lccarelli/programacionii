package primerparcial;

public class Deduccion extends Concepto {
    private double porcentaje;

    public Deduccion(String descripcion, double porcentaje) {
        super(descripcion);
        this.porcentaje = porcentaje;
    }

    @Override
    public double obtenerImporte(double totalBruto) {
        return totalBruto * (this.porcentaje / 100.0);
    }
}
