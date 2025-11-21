package archisolutions.app;

public class Nota {
    private Materia materia;
    private double valor;
    public Nota(Materia materia, double valor) {
        this.materia = materia;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

}
