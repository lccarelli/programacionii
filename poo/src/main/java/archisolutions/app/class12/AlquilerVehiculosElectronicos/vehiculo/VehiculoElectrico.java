package archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo;

public abstract class VehiculoElectrico {
    private static final double UMBRAL_CARGA_BAJA = 20.0;

    private final int id;
    private final ModeloVehiculo modeloVehiculo;
    private double nivelCargaActual;
    private double kilometrosRecorridos;
    protected final double tarifaPorHora;
    protected final double costoPorKm;
    private EstadoVehiculo estado;

    public VehiculoElectrico(int id, ModeloVehiculo modelo, double tarifaPorHora, double costoPorKm) {
        this.id = id;
        this.modeloVehiculo = modelo;
        this.tarifaPorHora = tarifaPorHora;
        this.costoPorKm = costoPorKm;
        this.nivelCargaActual = 100.0;
        this.kilometrosRecorridos = 0.0;
        this.estado = EstadoVehiculo.DISPONIBLE;
    }

    public ModeloVehiculo getModeloVehiculo() {
        return this.modeloVehiculo;
    }

    public void setKilometrosRecorridos(double kilometrosRecorridos) {
        this.kilometrosRecorridos += kilometrosRecorridos;
    }

    public abstract double calcularCostoAlquiler(int horas, double km);

    public abstract boolean necesitaMantenimiento();

    protected abstract double getConsumoPorKm();

    public boolean necesitaCarga() {
        return this.nivelCargaActual < UMBRAL_CARGA_BAJA;
    }

    public void actualizarEstado(EstadoVehiculo nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void reducirCarga(double kmRecorridos) {
        double consumoTotal = kmRecorridos * getConsumoPorKm();
        this.nivelCargaActual -= consumoTotal;
        if (this.nivelCargaActual < 0) {
            this.nivelCargaActual = 0;
        }
        this.actualizarKilometraje(kmRecorridos);
    }

    private void actualizarKilometraje(double km) {
        if (km > 0) {
            this.kilometrosRecorridos += km;
        }
    }

    public int getId() { return id; }
    public EstadoVehiculo getEstado() { return estado; }
    public double getKilometrosRecorridos() { return kilometrosRecorridos; }


    public void registrarUso(double km) {
        this.kilometrosRecorridos += km;
        reducirCarga(km);
    }


    @Override
    public String toString() {
        return "ID: " + id +
                ", Modelo: " + modeloVehiculo +
                ", Estado: " + estado +
                ", Carga: " + String.format("%.2f%%", nivelCargaActual) +
                ", KM Recorridos: " + kilometrosRecorridos;
    }
}
