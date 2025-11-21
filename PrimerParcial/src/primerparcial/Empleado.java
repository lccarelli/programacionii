package primerparcial;

import java.util.ArrayList;

public class Empleado {

    private String nombre;
    private String apellido;
    private String cuil;
    private double valorBase;
    private Sector sector;
    private ArrayList<Concepto> conceptos;

    public Empleado(String nombre, String apellido, String cuil, double valorBase, Sector sector, ArrayList<Concepto> conceptos) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuil = cuil;
        this.valorBase = valorBase;
        this.sector = sector;
        this.conceptos = conceptos;
    }

    public double getSueldoBasico() {
        return this.valorBase * this.sector.getCoeficiente();
    }

    public double getTotalBruto() {
        double sueldoBasico = getSueldoBasico();
        double totalOtrasRemuneraciones = 0;

        for (Concepto concepto : this.conceptos) {

            if (concepto instanceof RemuneracionFija || concepto instanceof RemuneracionVariable) {
                totalOtrasRemuneraciones += concepto.obtenerImporte(sueldoBasico);
            }
        }
        return sueldoBasico + totalOtrasRemuneraciones;
    }

    public double getSueldoAPagar() {
        double totalBruto = getTotalBruto();
        double totalDeducciones = 0;

        for (Concepto concepto : this.conceptos) {
            if (concepto instanceof Deduccion) {
                totalDeducciones += concepto.obtenerImporte(totalBruto);
            }
        }
        return totalBruto - totalDeducciones;
    }

    public void mostrarDetalleSueldo() {
        double sueldoBasico = getSueldoBasico();
        double totalBruto = getTotalBruto();
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("%-5s %-25s %15s %15s\n", "ID", "Descripción", "Remuneración", "Deducción"));
        sb.append("-----------------------------------------------------------------\n");
        sb.append(String.format("%-5d %-25s %15.2f\n", 0, "Sueldo Básico", sueldoBasico));
        
        for (Concepto c : conceptos) {
            if (c instanceof RemuneracionFija || c instanceof RemuneracionVariable) {
                sb.append(String.format("%-5d %-25s %15.2f\n", c.getId(), c.getDescripcion(), c.obtenerImporte(sueldoBasico)));
            } else if (c instanceof Deduccion) {
                double importeDeduccion = c.obtenerImporte(totalBruto);
                sb.append(String.format("%-5d %-25s %15s %15.2f\n", c.getId(), c.getDescripcion(), "", importeDeduccion));
            }
        }
        
        sb.append("-----------------------------------------------------------------\n");
        sb.append(String.format("SUELDO A PAGAR: %.2f\n", getSueldoAPagar()));
        
        System.out.print(sb);
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCuil() { return cuil; }
    public Sector getSector() { return sector; }
}
