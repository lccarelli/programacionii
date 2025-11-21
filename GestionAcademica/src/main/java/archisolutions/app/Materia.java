package archisolutions.app;

public enum Materia {
    MATEMATICA(0.20), PROGRAMACION(0.30), OTROS(0);
    private double bono;

    private Materia(double bono) {
        this.bono = bono;
    }

    public double getBono() {
        return bono;
    }
}
