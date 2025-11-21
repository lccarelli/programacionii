package primerparcial;


public enum Sector {
    ADMINISTRACION(1.15),
    VENTAS(1.20),
    PRODUCCION(1.10);

    private final double coeficiente;

    Sector(double coeficiente) {
        this.coeficiente = coeficiente;
    }

    public double getCoeficiente() {
        return coeficiente;
    }
}
