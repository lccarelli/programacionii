package archisolutions.app.class12.AlquilerVehiculosElectronicos;

import archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo.EstadoVehiculo;
import archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo.VehiculoElectrico;

public class Alquiler {
    private VehiculoElectrico vehiculo;
    private int horasAlquiler;
    private double kilometrosRecorridos;


    public void finalizarAlquiler(){
        this.vehiculo.actualizarEstado(EstadoVehiculo.DISPONIBLE);
        this.vehiculo.setKilometrosRecorridos(this.kilometrosRecorridos);
        this.vehiculo.reducirCarga(this.kilometrosRecorridos);
    }

    public void iniciarAlquiler(){
        this.vehiculo.actualizarEstado(EstadoVehiculo.ALQUILADO);
    }
}
