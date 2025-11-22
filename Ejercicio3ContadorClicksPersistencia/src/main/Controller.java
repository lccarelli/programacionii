package main;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {

    private static final String FILE_NAME = "contador.dat";

    @FXML
    private Label lblCount;

    private int count = 0;

    // Se llama automáticamente después de inyectar los @FXML
    @FXML
    private void initialize() {
        cargarContador();
        actualizarLabel();
    }

    @FXML
    private void onClick() {
        count++;
        actualizarLabel();
        guardarContador();
    }

    private void cargarContador() {
        Path path = Paths.get(FILE_NAME);

        if (Files.exists(path)) {
            try {
                String contenido = Files.readString(path).trim();
                if (!contenido.isEmpty()) {
                    count = Integer.parseInt(contenido);
                }
            } catch (Exception e) {
                // Si hay error (archivo corrupto, etc.), empezamos en 0
                count = 0;
            }
        } else {
            // Si no existe, empezamos en 0 y lo creamos
            count = 0;
            guardarContador();
        }
    }

    private void guardarContador() {
        Path path = Paths.get(FILE_NAME);
        try {
            Files.writeString(path, String.valueOf(count));
        } catch (IOException e) {
            e.printStackTrace(); // para el ejercicio alcanza con loguear
        }
    }

    private void actualizarLabel() {
        lblCount.setText("Clicks: " + count);
    }
}
