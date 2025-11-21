package archisolutions.app.class16.Pizzeria;

public class Especial {
    private Tamaño tamaño;
    private int cantidadFaina;
    private static final double PRECIO_FAINA = 500;

    public double calcularPrecio(){
        return PRECIO_FAINA * this.cantidadFaina;
    }
}
