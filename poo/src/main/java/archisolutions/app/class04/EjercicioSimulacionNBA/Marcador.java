package archisolutions.app.class04.EjercicioSimulacionNBA;

/**
 * Gestiona el marcador de un partido de baloncesto.
 * Mantiene un registro de los equipos local y visitante y sus respectivos puntajes.
 */
public class Marcador {
    /**
     * El equipo que juega como local.
     */
    private final Equipo local;
    /**
     * El equipo que juega como visitante.
     */
    private final Equipo visitante;
    /**
     * El puntaje actual del equipo local.
     */
    private int puntosLocal;
    /**
     * El puntaje actual del equipo visitante.
     */
    private int puntosVisitante;

    /**
     * Obtiene el equipo local.
     * @return El objeto Equipo local.
     */
    public Equipo getLocal() {
        return local;
    }

    /**
     * Obtiene el equipo visitante.
     * @return El objeto Equipo visitante.
     */
    public Equipo getVisitante() {
        return visitante;
    }

    /**
     * Obtiene los puntos del equipo local.
     * @return El puntaje del equipo local.
     */
    public int getPuntosLocal() {
        return puntosLocal;
    }

    /**
     * Obtiene los puntos del equipo visitante.
     * @return El puntaje del equipo visitante.
     */
    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    /**
     * Construye un nuevo marcador para un partido.
     * Inicializa los puntajes de ambos equipos en 0.
     *
     * @param local El equipo local.
     * @param visitante El equipo visitante.
     */
    public Marcador(Equipo local, Equipo visitante) {
        this.local = local;
        this.visitante = visitante;
    }

    /**
     * Suma una cantidad de puntos al marcador de un equipo específico.
     *
     * @param equipo El equipo que anotó los puntos.
     * @param puntos La cantidad de puntos a sumar (ej. 2 para un doble, 3 para un triple).
     */
    public void sumar(Equipo equipo, int puntos){
        if(equipo.sonIguales(this.local)){
            this.puntosLocal += puntos;
        }
        else if(equipo.sonIguales(this.visitante)){
            this.puntosVisitante += puntos;
        }
    }

}
