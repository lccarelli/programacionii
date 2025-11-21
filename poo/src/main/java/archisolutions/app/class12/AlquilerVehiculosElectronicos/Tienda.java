package archisolutions.app.class12.AlquilerVehiculosElectronicos;

import archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo.*;

import java.util.ArrayList;

public class Tienda {
    private static int ID_GENERADOR = 1;
    private ArrayList<VehiculoElectrico> vehiculos;
    private ArrayList<Alquiler> alquileres;

    public Tienda() {
        this.vehiculos = new ArrayList<>();
        this.alquileres = new ArrayList<>();
    }

    public void crearVehiculo(ModeloVehiculo modelo, double tarifaPorHora, double costoPorKm){
        switch (modelo.getTipo()){
            case AUTO:
                this.agregarVehiculo(new AutoElectrico(asignarId(), modelo, tarifaPorHora, costoPorKm));
                break;
            case SCOOTER:
                this.agregarVehiculo(new ScooterElectrico(asignarId(), modelo, tarifaPorHora, costoPorKm));
                break;
            case BICICLETA:
                this.agregarVehiculo(new BicicletaElectrica(asignarId(), modelo, tarifaPorHora, costoPorKm));
                break;
        }
    }

    public void agregarVehiculo(VehiculoElectrico vehiculo){
        this.vehiculos.add(vehiculo);
    }

    public ArrayList<VehiculoElectrico> getVehiculos(){
        return this.vehiculos;
    }

    public VehiculoElectrico buscarVehiculoPorId(int id){
        return null;
    }

    public ArrayList<VehiculoElectrico> getVehiculosQueNecesitanMantenimiento(){
        return null;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return alquileres;
    }

    public ArrayList<VehiculoElectrico> getVehiculosQueNecesitanCarga(){
        ArrayList<VehiculoElectrico> necesitanCarga = new ArrayList<>();
        for (VehiculoElectrico vehiculo : this.vehiculos) {
            if (vehiculo.necesitaCarga()) {
                necesitanCarga.add(vehiculo);
            }
        }
        return necesitanCarga;
    }

    private int asignarId(){
        return ID_GENERADOR++;
    }
}
