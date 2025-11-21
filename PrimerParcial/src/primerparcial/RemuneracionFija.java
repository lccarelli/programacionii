package primerparcial;

public class RemuneracionFija extends Concepto {
    private double importeFijo;
    private double basicoMinimo;

    public RemuneracionFija(String descripcion, double importeFijo, double basicoMinimo) {
        super(descripcion);
        this.importeFijo = importeFijo;
        this.basicoMinimo = basicoMinimo;
    }

    @Override
    public double obtenerImporte(double sueldoBasico) {
        return (sueldoBasico >= this.basicoMinimo) ? this.importeFijo : 0;
    }
}
