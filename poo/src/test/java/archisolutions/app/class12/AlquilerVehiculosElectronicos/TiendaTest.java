package archisolutions.app.class12.AlquilerVehiculosElectronicos;

import archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo.ModeloVehiculo;
import archisolutions.app.class12.AlquilerVehiculosElectronicos.vehiculo.VehiculoElectrico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class TiendaTest {
    private Tienda tienda;

    @BeforeEach
    void setUp() {
        tienda = new Tienda();
    }


    @Test
    void darAltaVehiculo_debeIncrementarElTamañoDeLaFlota() {
        // Arrange
        int tamañoInicial = tienda.getVehiculos().size();

        // Act
        tienda.crearVehiculo(ModeloVehiculo.FORD_FIESTA, 200, 18);

        // Assert
        assertThat(tienda.getVehiculos().size()).isEqualTo(tamañoInicial + 1);
    }

    @Test
    void buscarVehiculoPorId_cuandoIdExiste_debeRetornarElVehiculoCorrecto() {
        // Arrange
        tienda.crearVehiculo(ModeloVehiculo.KA, 300, 25); // Este tendrá ID 1

        // Act
        VehiculoElectrico vehiculoEncontrado = tienda.buscarVehiculoPorId(1);

        // Assert
        assertThat(vehiculoEncontrado).isNotNull();
        assertThat(vehiculoEncontrado.getId()).isEqualTo(1);
        assertThat(vehiculoEncontrado.getModeloVehiculo()).isEqualTo(ModeloVehiculo.KA);
    }

    // --- Pruebas de Integración ---

    @Test
    void getVehiculosQueNecesitanMantenimiento_debeRetornarSoloLosVehiculosCorrectos() {
        // Arrange: Damos de alta una flota mixta
        tienda.crearVehiculo(ModeloVehiculo.KA, 300, 25); // ID 1
        tienda.crearVehiculo(ModeloVehiculo.TREK_MARLIN_5, 100, 0);   // ID 2 (Bici umbral 2000km)
        tienda.crearVehiculo(ModeloVehiculo.FORD_FIESTA, 200, 18);  // ID 3

        // Act: Simulamos el uso
        VehiculoElectrico tesla = tienda.buscarVehiculoPorId(1);
        tesla.registrarUso(5000); // Este necesita mantenimiento

        VehiculoElectrico bicicleta = tienda.buscarVehiculoPorId(2);
        bicicleta.registrarUso(1500); // Este NO necesita

        // Assert: Verificamos que el método de la tienda filtre correctamente
        List<VehiculoElectrico> paraMantenimiento = tienda.getVehiculosQueNecesitanMantenimiento();

        assertThat(paraMantenimiento).hasSize(1);
        assertThat(paraMantenimiento.get(0).getId()).isEqualTo(1);
    }

    @Test
    void getVehiculosQueNecesitanCarga_debeRetornarSoloVehiculosConBateriaBaja() {
        // Arrange
        tienda.crearVehiculo(ModeloVehiculo.KA, 300, 25); // ID 1
        tienda.crearVehiculo(ModeloVehiculo.TREK_MARLIN_5, 100, 0);   // ID 2

        // Act
        VehiculoElectrico tesla = tienda.buscarVehiculoPorId(1);
        tesla.registrarUso(10); // Batería alta

        VehiculoElectrico bicicleta = tienda.buscarVehiculoPorId(2);
        bicicleta.registrarUso(550); // Batería baja (consumo 0.15%/km)

        // Assert
        // FIX: Llamar al método correcto. El método se llama igual que el de mantenimiento.
        List<VehiculoElectrico> paraCarga = tienda.getVehiculosQueNecesitanCarga();

        assertThat(paraCarga).hasSize(1);
        assertThat(paraCarga.get(0).getId()).isEqualTo(2);
    }
}