package primerparcial;

import java.util.ArrayList;
import java.util.Arrays;
import primerparcial.excepciones.CuilInexistenteException;

public class PrimerParcial {

    public static void main(String[] args) {
        // --- 1. CREACIÓN DE CONCEPTOS ---
        // Remuneraciones
        Concepto presentismo = new RemuneracionFija("Presentismo", 10000, 100000); // Se paga si el básico es >= 100000
        Concepto premioDesempeno = new RemuneracionVariable("Premio por Desempeño", 5); // 5% del básico

        // Deducciones
        Concepto jubilacion = new Deduccion("Aporte Jubilatorio", 11); // 11% del bruto
        Concepto obraSocial = new Deduccion("Obra Social", 3); // 3% del bruto

        // --- 2. CREACIÓN DE LISTAS DE CONCEPTOS PARA EMPLEADOS ---
        ArrayList<Concepto> conceptosEmp1 = new ArrayList<>(Arrays.asList(presentismo, jubilacion, obraSocial));
        ArrayList<Concepto> conceptosEmp2 = new ArrayList<>(Arrays.asList(presentismo, premioDesempeno, jubilacion, obraSocial));
        ArrayList<Concepto> conceptosEmp3 = new ArrayList<>(Arrays.asList(jubilacion, obraSocial));

        // --- 3. CREACIÓN DE EMPLEADOS ---
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        listaEmpleados.add(new Empleado("Juan", "Perez", "20-12345678-1", 100000, Sector.PRODUCCION, conceptosEmp1));
        listaEmpleados.add(new Empleado("Ana", "Gomez", "27-23456789-2", 150000, Sector.ADMINISTRACION, conceptosEmp2));
        listaEmpleados.add(new Empleado("Luis", "Castro", "20-34567890-3", 90000, Sector.PRODUCCION, conceptosEmp3));

        // --- 4. CREACIÓN DE LA FÁBRICA ---
        Fabrica miFabrica = new Fabrica("Fábrica de Software S.A.", listaEmpleados);

        // --- 5. EJECUCIÓN DE FUNCIONALIDADES ---
        StringBuilder sb = new StringBuilder();

        sb.append("==========================================================\n");
        sb.append("1. LISTADO DE TODOS LOS SUELDOS A PAGAR\n");
        sb.append("==========================================================\n");
        System.out.print(sb);
        miFabrica.listarTodosLosSueldosAPagar();

        sb.setLength(0);
        sb.append("\n==========================================================\n");
        sb.append("2. MOSTRAR CONCEPTOS DE UN EMPLEADO (EXISTENTE)\n");
        sb.append("==========================================================\n");
        System.out.print(sb);
        try {
            miFabrica.mostrarConceptosDeUnEmpleado("27-23456789-2");
        } catch (CuilInexistenteException e) {
            System.out.println("Error: " + e.getMessage()); //Manejo exepcion
        }

        sb.setLength(0);
        sb.append("\n==========================================================\n");
        sb.append("3. MOSTRAR CONCEPTOS DE UN EMPLEADO (INEXISTENTE)\n");
        sb.append("==========================================================\n");
        System.out.print(sb);
        try {
            miFabrica.mostrarConceptosDeUnEmpleado("99-99999999-9");
        } catch (CuilInexistenteException e) {
            System.out.println("Mensaje de error amigable: " + e.getMessage()); //Manejo exepcion
        }

        sb.setLength(0);
        sb.append("\n==========================================================\n");
        sb.append("4. OBTENER EMPLEADOS CON MEJOR SUELDO DEL SECTOR PRODUCCION\n");
        sb.append("==========================================================\n");
        ArrayList<Empleado> mejores = miFabrica.obtenerEmpleadosMejorSueldoUnSector(Sector.PRODUCCION);
        if (mejores.isEmpty()) {
            sb.append("No se encontraron empleados en ese sector.\n");
        } else {
            sb.append("Empleado/s con el sueldo más alto en PRODUCCION:\n");
            for (Empleado emp : mejores) {
                sb.append("- ").append(emp.getNombre()).append(" ").append(emp.getApellido()).append(" (Sueldo: $").append(String.format("%.2f", emp.getSueldoAPagar())).append(")\n");
            }
        }
        System.out.print(sb);

        sb.setLength(0);
        sb.append("\n==========================================================\n");
        sb.append("5. OBTENER DETALLE DE TODOS LOS SUELDOS A PAGAR Y SUS CONCEPTOS\n");
        sb.append("==========================================================\n");
        System.out.print(sb);
        try {
            miFabrica.listarTodosLosSueldosAPagarYConceptos();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage()); //Manejo exepcion
        }
    }
}
