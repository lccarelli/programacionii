package archisolutions.app.class04.EjercicioSimulacionNBA;

import java.util.Random;
import java.util.Scanner;

/**
 * Punto de entrada principal para la aplicación de simulación de partidos de la NBA.
 * Esta clase se encarga de interactuar con el usuario para configurar el partido
 * y luego iniciar la simulación.
 */
public class Main {
    /**
     * Método principal que se ejecuta al iniciar el programa.
     * <p>
     * El flujo es el siguiente:
     * 1. Pide al usuario por consola los nombres de los equipos local y visitante.
     * 2. Crea las instancias de {@link Equipo} y {@link Partido}.
     * 3. Llama al método {@link Partido#jugar()} para comenzar la simulación.
     * 4. Cierra el scanner al finalizar.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1. Pedir por consola al equipo local y visitante.
        System.out.println("🏀 ¡Bienvenido al Simulador de Partidos de la NBA! 🏀");
        System.out.print("Ingrese el nombre del equipo LOCAL: ");
        String nombreLocal = scanner.nextLine();
        System.out.print("Ingrese el nombre del equipo VISITANTE: ");
        String nombreVisitante = scanner.nextLine();

        // 2. Crear objetos Equipo y Partido.
        Equipo equipoLocal = new Equipo(nombreLocal);
        Equipo equipoVisitante = new Equipo(nombreVisitante);
        Random random = new Random();

        // 3. Simular partido.
        Partido partido = new Partido(equipoLocal, equipoVisitante, random);
        partido.jugar();
        scanner.close();
    }
}
