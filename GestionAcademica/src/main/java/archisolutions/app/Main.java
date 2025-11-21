package archisolutions.app;

import archisolutions.app.excepciones.DniDuplicadoException;
import archisolutions.app.excepciones.PersonaNoEncontradaException;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Inicializamos la institución y las materias
        Institucion institucion = new Institucion();

        System.out.println("--- 1. REGISTRO DE PERSONAS ---");

        try {
            // Creamos y registramos alumnos
            Alumno alumno1 = new Alumno("Juan Perez", 40123456, 20);
            Alumno alumno2 = new Alumno("Ana Gomez", 41987654, 19);
            Alumno alumno3 = new Alumno("Carlos Diaz", 42333444, 18);
            institucion.altaPersona(alumno1);
            institucion.altaPersona(alumno2);
            institucion.altaPersona(alumno3);
            System.out.println("Alumnos registrados exitosamente.");

            // Creamos y registramos profesores
            Profesor profesorMatematica = new Profesor("Ricardo Tapia", 25111222, 45, Materia.MATEMATICA, 100000);
            Profesor profesorProgramacion = new Profesor("Silvia Rojas", 18333444, 66, Materia.PROGRAMACION, 100000);

            institucion.altaPersona(profesorMatematica);
            institucion.altaPersona(profesorProgramacion);
            System.out.println("Profesores registrados exitosamente.");

            // Creamos y registramos administrativos
            Administrativo admin1 = new Administrativo("Laura Pausini", 30555666, 35, 100000);
            institucion.altaPersona(admin1);
            System.out.println("Administrativos registrados exitosamente.");

            // Intentamos agregar un DNI duplicado para probar la excepción
            System.out.println("\nIntentando registrar persona con DNI duplicado...");
            institucion.altaPersona(new Alumno("Juan Perez Clon", 40123456, 21));

        } catch (DniDuplicadoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n--- 2. GESTIÓN DE ALUMNOS Y NOTAS ---");
        // Buscamos al alumno "Ana Gomez" para agregarle notas
        try {
            Alumno ana = (Alumno) institucion.buscarPersona(41987654);
            ana.agregarNota(Materia.MATEMATICA, 3.5);
            ana.agregarNota(Materia.PROGRAMACION, 2.0);
            System.out.println("Notas agregadas a " + ana.getNombre());
            System.out.printf("Promedio de %s: %.2f\n", ana.getNombre(), ana.calcularPromedio());

            Alumno juan = (Alumno) institucion.buscarPersona(40123456);
            juan.agregarNota(Materia.MATEMATICA, 8.0);
            juan.agregarNota(Materia.PROGRAMACION, 9.5);
            System.out.println("Notas agregadas a " + juan.getNombre());
            System.out.printf("Promedio de %s: %.2f\n", juan.getNombre(), juan.calcularPromedio());

        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- 3. CÁLCULO DE SUELDOS ---");
        try {
            Profesor profeProg = (Profesor) institucion.buscarPersona(25111222);
            System.out.printf("Sueldo final de %s: $%.2f\n", profeProg.getNombre(), profeProg.calcularSueldoFinal());

            Administrativo admin = (Administrativo) institucion.buscarPersona(30555666);
            admin.cargarHorasExtras(10); // Le cargamos 10 horas extras
            admin.cargarHorasExtras(35); // Le cargamos otras 35 (total 45)
            System.out.printf("Sueldo final de %s (con %d hs extras): $%.2f\n", admin.getNombre(), admin.getHorasExtras(), admin.calcularSueldoFinal());

        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- 4. REPORTE: ALUMNOS APROBADOS (PROMEDIO >= 7) ---");
        ArrayList<Alumno> aprobados = institucion.listarAlumnosConPromedioMayorOIgualASiete();
        if (aprobados.isEmpty()) {
            System.out.println("No hay alumnos aprobados con promedio mayor o igual a 7.");
        } else {
            for (Alumno alumno : aprobados) {
                System.out.printf("- %s (Promedio: %.2f)\n", alumno.getNombre(), alumno.calcularPromedio());
            }
        }

        System.out.println("\n--- 5. BÚSQUEDA DE PERSONA POR DNI ---");
        try {
            int dniBuscado = 18333444;
            Persona personaEncontrada = institucion.buscarPersona(dniBuscado);
            System.out.printf("Persona encontrada con DNI %d: %s (ID: %d, Edad: %d)\n",
                    dniBuscado, personaEncontrada.getNombre(), personaEncontrada.getId(), personaEncontrada.getEdad());
        } catch (PersonaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- 6. REPORTE: RECAUDACIÓN TOTAL EN SUELDOS ---");
        System.out.printf("El total a pagar en sueldos (profesores y administrativos) es: $%.2f\n", institucion.calcularRecaudacionTotalSueldos());

        System.out.println("\n--- 7. REPORTE: PERSONAS QUE REQUIEREN ATENCIÓN ESPECIAL ---");
        ArrayList<Persona> requierenAtencion = institucion.listarPersonasQueRequierenAtencion();
        for (Persona persona : requierenAtencion) {
            System.out.printf("- %s (ID: %d, Tipo: %s)\n", persona.getNombre(), persona.getId(), persona.getClass().getSimpleName());
        }

        System.out.println("\n--- 8. REPORTE: PROFESORES CON SUELDO MAYOR A $100.000 ---");
        ArrayList<Profesor> profesoresConSueldoMayor = institucion.mostrarProfesoresConSueldoMayorA(100000);
        if (profesoresConSueldoMayor.isEmpty()) {
            System.out.println("No hay profesores con sueldo mayor a $100.000.");
        }
        for (Profesor profesor : profesoresConSueldoMayor) {
            System.out.printf(
                    "- %s (ID: %d, Materia: %s, Sueldo: $%.2f)\n",
                    profesor.getNombre(),
                    profesor.getId(),
                    profesor.getMateria(),
                    profesor.calcularSueldoFinal()
            );
        }

        System.out.println("\n--- 9. REPORTE: ADMINISTRATIVOS CON MÁS DE 40 HORAS EXTRAS ---");
        ArrayList<Administrativo> administrativosConMasHorasExtras = institucion.mostrarAdministrativosConMasHorasExtras(40);
        if (administrativosConMasHorasExtras.isEmpty()) {
            System.out.println("No hay administrativos con más de 40 horas extra");
        }
        for (Administrativo administrativo : administrativosConMasHorasExtras) {
            System.out.printf(
                    "- %s (ID: %d, Horas Extras: %d, Sueldo: $%.2f",
                    administrativo.getNombre(),
                    administrativo.getId(),
                    administrativo.getHorasExtras(),
                    administrativo.calcularSueldoFinal()
            );
        }

    }
}