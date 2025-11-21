package archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo;

public enum ModeloVehiculo {

    //MODELOS AUTOS
    PEUGEOT_505(TipoVehiculo.AUTO),
    FORD_FIESTA(TipoVehiculo.AUTO),
    TWINGO(TipoVehiculo.AUTO),
    KA(TipoVehiculo.AUTO),
    FOCUS(TipoVehiculo.AUTO),

    //MODELOS SCOOTER
    IAOMI_MI_ELECTRIC_3(TipoVehiculo.SCOOTER),
    SEGWAY_NINEBOT_MAX(TipoVehiculo.SCOOTER),

    // MODELOS BICICLETA
    TREK_MARLIN_5(TipoVehiculo.BICICLETA),
    GIANT_TALON_2(TipoVehiculo.BICICLETA);

    private final TipoVehiculo tipo;

    ModeloVehiculo(TipoVehiculo tipo){
        this.tipo = tipo;
    }

    public TipoVehiculo getTipo(){
        return this.tipo;
    }
}
