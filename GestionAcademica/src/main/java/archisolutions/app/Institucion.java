package archisolutions.app;

import archisolutions.app.excepciones.DniDuplicadoException;
import archisolutions.app.excepciones.PersonaNoEncontradaException;

import java.util.ArrayList;

public class Institucion {
    private ArrayList<Persona> personas = new ArrayList<>();

    public Institucion() {

    }
    public void altaPersona(Persona nuevaPersona) throws DniDuplicadoException {

        for (Persona persona : personas){
            if(persona.getDni() == nuevaPersona.getDni()){
                throw new DniDuplicadoException();
            }
        }
        personas.add(nuevaPersona);
    }

    // REPORTES Y BUSQUEDAS
    public Persona buscarPersona(String nombre){
        for (Persona persona : personas){
            if(persona.getNombre().equals(nombre)){
                return persona;
            }
        }
        throw new PersonaNoEncontradaException();
    }

    public Persona buscarPersona(int dni){
        for (Persona persona : personas){
            if(persona.getDni() == dni){
                return persona;
            }
        }
        throw new PersonaNoEncontradaException();
    }

    public ArrayList<Alumno> listarAlumnosConPromedioMayorOIgualASiete(){
        ArrayList<Alumno> alumnosConMasDeSiete = new ArrayList<>();
        for(Persona persona : personas){
            if(persona instanceof Alumno && ((Alumno) persona).calcularPromedio()>=7) {
                alumnosConMasDeSiete.add((Alumno) persona);
            }
        }

        return alumnosConMasDeSiete;
    }

    public ArrayList<Profesor> mostrarProfesoresConSueldoMayorA(double sueldo){
        ArrayList<Profesor> profesoresConSueldoMayor = new ArrayList<>();
        for(Persona persona : personas){
            if(persona instanceof Profesor && persona.calcularSueldoFinal()>sueldo) {
                profesoresConSueldoMayor.add((Profesor) persona);
            }
        }
        return profesoresConSueldoMayor;
    }

    public ArrayList<Administrativo> mostrarAdministrativosConMasHorasExtras(int horasExtras){
        ArrayList<Administrativo> administrativosConMasHorasExtras = new ArrayList<>();
        for(Persona persona : personas){
            if(persona instanceof Administrativo && persona.calcularSueldoFinal()>horasExtras){
                administrativosConMasHorasExtras.add((Administrativo) persona);
            }

        }
        return administrativosConMasHorasExtras;
    }

    public ArrayList<Persona> listarPersonasQueRequierenAtencion(){
        ArrayList<Persona> personasQueRequierenAtencion = new ArrayList<>();
        for(Persona persona : personas){
            if(persona.requiereAtencion()){
                personasQueRequierenAtencion.add(persona);
            }
        }
        return personasQueRequierenAtencion;
    }

    public double calcularRecaudacionTotalSueldos(){
        double recaudacionTotal = 0;
        for(Persona persona : personas){
            if (persona instanceof Profesor || persona instanceof Administrativo){
                recaudacionTotal += persona.calcularSueldoFinal();
            }
        }
        return recaudacionTotal;
    }
}
