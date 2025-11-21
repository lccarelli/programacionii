package archisolutions.app.class04.EjercicioSimulacionNBA;

/**
 * Gestiona el tiempo de un partido de baloncesto.
 * Controla los segundos, minutos y el cuarto actual, y determina cuándo finaliza el tiempo regular.
 */
public class RelojPartido {
    private int cuarto;
    private int minutos;
    private int segundos;

    /**
     * Duración en minutos de cada cuarto.
     */
    private static final int MINUTOS_POR_CUARTO = 12;
    /**
     * Número total de cuartos en el tiempo regular de un partido.
     */
    private static final int CUARTOS_TIEMPO_REGULAR = 4;

    /**
     * Construye un nuevo reloj de partido, inicializado al comienzo del primer cuarto (00:00).
     */
    public RelojPartido() {
        this.cuarto = 1;
        this.minutos = 0;
        this.segundos = 0;
    }

    /**
     * Avanza el reloj una cantidad determinada de segundos.
     * Si los segundos superan 59, avanza los minutos correspondientes.
     * Si los minutos alcanzan la duración de un cuarto, avanza al siguiente cuarto y reinicia los minutos.
     *
     * @param segundosAvance El número de segundos que avanzará el reloj.
     */
    public void avanzarSegundos(int segundosAvance){
        this.segundos += segundosAvance;
        if(this.segundos >= 60){
            this.minutos += this.segundos / 60;
            this.segundos %= 60;
        }
        if(this.minutos >= MINUTOS_POR_CUARTO){
            this.cuarto++;
            this.minutos -= MINUTOS_POR_CUARTO;
        }
    }

    /**
     * Obtiene el minuto actual dentro del cuarto.
     * @return El minuto actual (0-11).
     */
    public int getMinutoDelCuarto(){
        return this.minutos;
    }

    /**
     * Obtiene los segundos actuales dentro del minuto.
     * @return Los segundos actuales (0-59).
     */
    public int getSegundosDelCuarto(){
        return this.segundos;
    }

    /**
     * Devuelve una representación en texto del cuarto actual.
     * @return Un String como "1er cuarto", "2do cuarto", etc., o "Tiempo extra".
     */
    public String getCuarto(){
        return switch (this.cuarto) {
            case 1 -> "1er cuarto";
            case 2 -> "2do cuarto";
            case 3 -> "3er cuarto";
            case 4 -> "4to cuarto";
            default -> "Tiempo extra";
        };
    }

    /**
     * Verifica si el tiempo regular del partido ha finalizado.
     * @return {@code true} si se han completado los 4 cuartos, {@code false} en caso contrario.
     */
    public boolean finalizoTiempoRegular(){
        return this.cuarto > CUARTOS_TIEMPO_REGULAR;
    }
}
