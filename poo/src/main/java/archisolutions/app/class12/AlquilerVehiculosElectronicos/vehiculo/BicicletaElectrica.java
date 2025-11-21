package archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo;

public class BicicletaElectrica extends VehiculoElectrico {
    private static final double CONSUMO_BATERIA_POR_KM = 0.2;
    private static final int KM_PARA_MANTENIMIENTO = 5000;

    public BicicletaElectrica(int id, ModeloVehiculo modelo, double tarifaPorHora, double costoPorKm) {
        super(id, modelo, tarifaPorHora, costoPorKm);
    }

    @Override
    public double calcularCostoAlquiler(int horas, double km) {
        return (horas * this.tarifaPorHora) + (km * this.costoPorKm);
    }

    @Override
    public boolean necesitaMantenimiento() {
        return this.getKilometrosRecorridos() >= KM_PARA_MANTENIMIENTO;
    }

    @Override
    protected double getConsumoPorKm() {
        return CONSUMO_BATERIA_POR_KM;
    }
}
