package primerparcial;

import primerparcial.excepciones.CuilInexistenteException;
import primerparcial.excepciones.EmpleadoNoEncontradoException;

import java.util.ArrayList;

public class Fabrica {
    private String nombre;
    private ArrayList<Empleado> empleados;

    public Fabrica(String nombre, ArrayList<Empleado> empleados) {
        this.nombre = nombre;
        this.empleados = empleados;
    }

    public void listarTodosLosSueldosAPagar() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-20s %15s\n", "Empleado", "C.U.I.L.", "Sueldo a pagar"));
        sb.append("----------------------------------------------------------\n");
        for (Empleado emp : empleados) {
            String nombreCompleto = emp.getNombre() + " " + emp.getApellido();
            sb.append(String.format("%-20s %-20s %15.2f\n", nombreCompleto, emp.getCuil(), emp.getSueldoAPagar()));
        }
        System.out.print(sb);
    }

    public void mostrarConceptosDeUnEmpleado(String cuil) throws CuilInexistenteException {
        Empleado empleadoEncontrado = null;
        for (Empleado emp : empleados) {
            if (emp.getCuil().equals(cuil)) {
                empleadoEncontrado = emp;
                break;
            }
        }

        if (empleadoEncontrado != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("----------------------------------------------------------\n");
            sb.append("Detalle de sueldo para: ").append(empleadoEncontrado.getNombre()).append(" ").append(empleadoEncontrado.getApellido()).append("\n");
            System.out.print(sb);
            empleadoEncontrado.mostrarDetalleSueldo();
        } else {
            throw new CuilInexistenteException("No existe el empleado con CUIL " + cuil);
        }
    }

    public ArrayList<Empleado> obtenerEmpleadosMejorSueldoUnSector(Sector sector) throws EmpleadoNoEncontradoException {

        ArrayList<Empleado> empleadosDelSector = new ArrayList<>();
        ArrayList<Empleado> empleadosConMejorSueldo = new ArrayList<>();

        for (Empleado emp : this.empleados) {
            if (emp.getSector() == sector) {
                empleadosDelSector.add(emp);
            }
        }

        if (empleadosDelSector.isEmpty()) {
            throw new EmpleadoNoEncontradoException();

        } else {
            double maxSueldo = 0;
            for (Empleado emp : empleadosDelSector) {
                if (emp.getSueldoAPagar() > maxSueldo) {
                    maxSueldo = emp.getSueldoAPagar();
                }
            }

            for (Empleado emp : empleadosDelSector) {
                if (emp.getSueldoAPagar() == maxSueldo) {
                    empleadosConMejorSueldo.add(emp);
                }
            }
        }

        return empleadosConMejorSueldo;
    }

    public void listarTodosLosSueldosAPagarYConceptos() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-20s %15s\n", "Empleado", "C.U.I.L.", "Sueldo a pagar"));
        sb.append("----------------------------------------------------------\n");
        for (Empleado emp : empleados) {
            String nombreCompleto = emp.getNombre() + " " + emp.getApellido();
            sb.append(String.format("%-20s %-20s %15.2f\n", nombreCompleto, emp.getCuil(), emp.getSueldoAPagar()));
            mostrarConceptosDeUnEmpleado(emp.getCuil());
        }
        System.out.print(sb);
    }

}
