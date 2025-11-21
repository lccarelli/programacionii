package archisolutions.app.class04.EjercicioSimulacionNBA;

/**
 * Representa a un equipo de baloncesto con un nombre.
 * Esta clase es inmutable.
 */
public class Equipo {
    /**
     * El nombre del equipo.
     */
    private final String nombre;

    /**
     * Construye una nueva instancia de Equipo.
     *
     * @param nombre El nombre para el equipo.
     */
    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del equipo.
     *
     * @return El nombre del equipo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Compara este equipo con otro para ver si son iguales.
     * La comparación se basa en el nombre del equipo.
     *
     * @param equipo El otro equipo con el que se va a comparar.
     * @return {@code true} si los nombres son iguales, {@code false} en caso contrario.
     */
    public boolean sonIguales(Equipo equipo){
        return this.nombre.equals(equipo.getNombre());
    }
}
