package archisolutions.app.class04.EjercicioSimulacionNBA;

import java.util.Random;

/**
 * Representa y orquesta la simulación de un partido de baloncesto de la NBA.
 * Esta clase es el motor principal del juego, gestionando el flujo, el reloj,
 * el marcador y las jugadas entre dos equipos.
 */
public class Partido {
    /**
     * Pausa en milisegundos entre cada jugada para hacer la simulación visible.
     */
    private static final int PAUSA_ENTRE_JUGADAS_MS = 1000;
    private final Marcador marcador;
    private final RelojPartido reloj;
    private final Random random;

    /**
     * Representa el resultado de una jugada fallida (0 puntos).
     */
    private static final int FALLO = 0;
    /**
     * Representa el resultado de una canasta de dos puntos.
     */
    private static final int DOBLE = 2;
    /**
     * Representa el resultado de una canasta de tres puntos.
     */
    private static final int TRIPLE = 3;

    /**
     * Constructor para crear una nueva instancia de un partido.
     *
     * @param local     El equipo que juega como local.
     * @param visitante El equipo que juega como visitante.
     * @param random    Una instancia de Random para generar resultados aleatorios.
     */
    public Partido(Equipo local, Equipo visitante, Random random) {
        this.marcador = new Marcador(local, visitante);
        this.reloj = new RelojPartido();
        this.random = random;
    }

    /**
     * Inicia y ejecuta el flujo principal de la simulación del partido.
     * Este método contiene la lógica central que simula un partido completo, jugada a jugada.
     * <p>
     * El flujo completo del partido es el siguiente:
     * <ol>
     *     <li><b>Inicio del Partido:</b> Se imprime un mensaje de bienvenida.</li>
     *     <li><b>Sorteo de Posesión:</b> Se decide al azar qué equipo (local o visitante) tendrá la primera posesión del balón.</li>
     *     <li><b>Bucle Principal del Juego:</b> Se inicia un bucle que continúa mientras el tiempo regular no haya finalizado ({@code !reloj.finalizoTiempoRegular()}). Cada iteración del bucle representa una posesión y su jugada.</li>
     *     <li><b>Dentro del Bucle (por jugada):</b>
     *         <ol type="a">
     *             <li>Se guarda el nombre del cuarto actual para detectar cuándo cambia.</li>
     *             <li>Se simula una jugada para el equipo con posesión ({@link #simularJugada(Equipo)}).</li>
     *             <li>Se imprime el estado actual del juego, incluyendo el marcador, el tiempo y el resultado de la jugada ({@link #imprimirJuego(Equipo, String)}).</li>
     *             <li>El reloj del partido avanza 20 segundos.</li>
     *             <li>Se introduce una breve pausa para que la simulación sea visible ({@link #simularTiempo()}).</li>
     *             <li>Se comprueba si ha terminado un cuarto. Si es así, se imprime un resumen del cuarto y el marcador parcial.</li>
     *             <li>La posesión del balón se pasa al equipo rival.</li>
     *         </ol>
     *     </li>
     *     <li><b>Fin del Partido:</b> Una vez que el bucle termina, se imprime un mensaje de fin de tiempo regular y el resultado final del partido ({@link #imprimirResultadoFinal()}).</li>
     * </ol>
     */
    public void jugar(){
        System.out.println("<*> COMIENZA EL PARTIDO <*>");
        System.out.println("---------------------------");

        // Decide al azar quien empieza con la posesion
        Equipo equipoConPosesion = random.nextBoolean() ? marcador.getLocal() : marcador.getVisitante();
        while (!reloj.finalizoTiempoRegular()) {
            String cuartoAnterior = reloj.getCuarto();
            // Simula una jugada
            String jugada = simularJugada(equipoConPosesion);
            // Imprime estado del juego y la jugada
            imprimirJuego(equipoConPosesion, jugada);
            // Avanza el tiempo a 20 segundos
            reloj.avanzarSegundos(20);
            simularTiempo();
            
            if (!cuartoAnterior.equals(reloj.getCuarto()) && !reloj.finalizoTiempoRegular()) {
                System.out.println("\n--- FIN DEL " + cuartoAnterior.toUpperCase() + " ---");
                // Imprimimos el marcador parcial sin modificar la clase Marcador
                System.out.printf("Marcador: %s %d - %d %s%n\n",
                        marcador.getLocal().getNombre(),
                        marcador.getPuntosLocal(),
                        marcador.getPuntosVisitante(),
                        marcador.getVisitante().getNombre());
                simularTiempo(); // Pausa extra entre cuartos
            }
            
            // Pasar la posicion al rival
            equipoConPosesion = equipoConPosesion.sonIguales(marcador.getLocal())
                    ? marcador.getVisitante()
                    : marcador.getLocal();
        }

        imprimirResultadoFinal();
    }

    /**
     * Simula el resultado de una única jugada de ataque.
     * @return int - Devuelve 0 (fallo), 2 (doble) o 3 (triple) de forma aleatoria.
     */
    private int resultadoAleatorio(){
        int[] posiblesResultados = {FALLO, DOBLE, TRIPLE};
        int indice = random.nextInt(posiblesResultados.length);
        return posiblesResultados[indice];
    }

    /**
     * Procesa una jugada completa para un equipo.
     * Obtiene un resultado aleatorio, actualiza el marcador si es necesario
     * y genera un texto descriptivo de la jugada.
     *
     * @param equipo El equipo que realiza la jugada.
     * @return Un String describiendo el resultado (ej: "(doble de Lakers)").
     */
    private String simularJugada(Equipo equipo){
        int puntos = resultadoAleatorio();
        String nombreEquipo = equipo.getNombre();

        return switch (puntos) {
            case FALLO -> "(fallo de " + nombreEquipo + ")";
            case DOBLE -> {
                marcador.sumar(equipo, DOBLE);
                yield "(doble de " + nombreEquipo + ")";
            }
            case TRIPLE -> {
                marcador.sumar(equipo, TRIPLE);
                yield "(triple de " + nombreEquipo + ")";
            }
            default -> "(jugada inesperada)"; // No debería ocurrir
        };
    }

    /**
     * Imprime en consola una línea que representa el estado actual del juego tras una jugada.
     * Formato: Min 0:00 (1er cuarto) - Warriors 0 - 0 Lakers (posesion de Warriors) -> (fallo de Warriors)
     *
     * @param equipoConPosesion El equipo que acaba de tener la posesión.
     * @param jugada            El texto que describe el resultado de la jugada.
     */
    private void imprimirJuego(Equipo equipoConPosesion, String jugada) {
        System.out.printf("Min %d:%02d (%s) - %s %d - %d %s (posesion de %s) -> %s%n",
                reloj.getMinutoDelCuarto(),
                reloj.getSegundosDelCuarto(),
                reloj.getCuarto(),
                marcador.getLocal().getNombre(),
                marcador.getPuntosLocal(),
                marcador.getPuntosVisitante(),
                marcador.getVisitante().getNombre(),
                equipoConPosesion.getNombre(),
                jugada
        );
    }

    /**
     * Imprime el mensaje de fin de partido y el marcador final.
     */
    private void imprimirResultadoFinal() {
        System.out.println("\n==================================================");
        System.out.println(">> FIN DEL TIEMPO REGULAR <<");
        System.out.println("==================================================");
        System.out.println("Resultado Final:");
        System.out.printf("%s %d - %d %s%n",
                marcador.getLocal().getNombre(),
                marcador.getPuntosLocal(),
                marcador.getPuntosVisitante(),
                marcador.getVisitante().getNombre());
    }

    /**
     * Pausa la ejecución del hilo actual para dar un efecto visual a la simulación.
     * Maneja la posible InterruptedException.
     */
    private void simularTiempo() {
        try {
            Thread.sleep(PAUSA_ENTRE_JUGADAS_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La simulación fue interrumpida.");
        }
    }
}
