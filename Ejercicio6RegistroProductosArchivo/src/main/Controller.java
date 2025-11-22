package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Controller {

    private static final String FILE_NAME = "productos.txt";

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private ComboBox<String> cmbCategoria;

    @FXML
    private TableView<Producto> tblProductos;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, Double> colPrecio;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private Label lblMensaje;

    private ObservableList<Producto> productos;

    @FXML
    private void initialize() {
        productos = FXCollections.observableArrayList();
        tblProductos.setItems(productos);

        colNombre.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("precio"));
        colCategoria.setCellValueFactory(
                new javafx.scene.control.cell.PropertyValueFactory<>("categoria"));

        cmbCategoria.getItems().addAll(
                "Alimentos",
                "Electrónica",
                "Ropa",
                "Hogar",
                "Otros"
        );
        cmbCategoria.getSelectionModel().select("Otros");

        cargarDesdeArchivo();
    }

    @FXML
    private void onAgregar() {
        limpiarMensaje();

        String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
        String precioStr = txtPrecio.getText() != null ? txtPrecio.getText().trim() : "";
        String categoria = cmbCategoria.getValue();

        if (nombre.isEmpty()) {
            mostrarMensaje("El nombre no puede estar vacío.", false);
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioStr.replace(",", "."));
        } catch (NumberFormatException e) {
            mostrarMensaje("El precio debe ser un número válido.", false);
            return;
        }

        if (precio < 0) {
            mostrarMensaje("El precio no puede ser negativo.", false);
            return;
        }

        if (categoria == null || categoria.trim().isEmpty()) {
            mostrarMensaje("Debe seleccionar una categoría.", false);
            return;
        }

        Producto p = new Producto(nombre, precio, categoria);
        productos.add(p);

        guardarEnArchivo();

        txtNombre.clear();
        txtPrecio.clear();
        cmbCategoria.getSelectionModel().select("Otros");

        mostrarMensaje("Producto agregado.", true);
    }

    @FXML
    private void onEliminar() {
        limpiarMensaje();

        Producto seleccionado = tblProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("No hay producto seleccionado para eliminar.", false);
            return;
        }

        productos.remove(seleccionado);
        guardarEnArchivo();

        mostrarMensaje("Producto eliminado.", true);
    }

    /**
     * Muestra un modal con el total de productos por categoría
     * y el total general.
     */
    @FXML
    private void onVerResumen() {
        limpiarMensaje();

        if (productos.isEmpty()) {
            mostrarMensaje("No hay productos cargados para resumir.", false);
            return;
        }

        // Agrupa por categoría y suma precio
        Map<String, Double> totalPorCategoria =
                productos.stream()
                        .collect(Collectors.groupingBy(
                                Producto::getCategoria,
                                TreeMap::new, // orden alfabético por categoría
                                Collectors.summingDouble(Producto::getPrecio)
                        ));

        double totalGeneral = totalPorCategoria.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        StringBuilder sb = new StringBuilder("Resumen por categoría:\n\n");
        for (Map.Entry<String, Double> entry : totalPorCategoria.entrySet()) {
            sb.append(entry.getKey())
              .append(": $")
              .append(String.format(Locale.US, "%.2f", entry.getValue()))
              .append("\n");
        }
        sb.append("\nTotal general: $")
          .append(String.format(Locale.US, "%.2f", totalGeneral));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resumen de productos");
        alert.setHeaderText("Total por categoría");
        alert.setContentText(sb.toString());
        alert.showAndWait();
    }

    // ---------- persistencia en TXT (CSV simple con ;) ----------

    private void guardarEnArchivo() {
        File archivo = new File(FILE_NAME);

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo, false))) {

            // Encabezado
            pw.println("nombre;precio;categoria");

            for (Producto p : productos) {
                String nombre = sanitize(p.getNombre());
                String categoria = sanitize(p.getCategoria());
                double precio = p.getPrecio();
                pw.printf(Locale.US, "%s;%.2f;%s%n", nombre, precio, categoria);
            }

        } catch (IOException e) {
            mostrarMensaje("Error al guardar productos: " + e.getMessage(), false);
        }
    }

    private String sanitize(String text) {
        if (text == null) return "";
        return text.replace(";", ",")
                   .replace("\n", " ")
                   .replace("\r", " ");
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(FILE_NAME);
        if (!archivo.exists()) {
            return;
        }

        List<Producto> cargados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                // saltar encabezado
                if (primera && linea.toLowerCase().startsWith("nombre;")) {
                    primera = false;
                    continue;
                }
                primera = false;

                String[] partes = linea.split(";");
                if (partes.length < 3) {
                    continue;
                }

                String nombre = partes[0].trim();
                String precioStr = partes[1].trim();
                String categoria = partes[2].trim();

                double precio;
                try {
                    precio = Double.parseDouble(precioStr.replace(",", "."));
                } catch (NumberFormatException e) {
                    continue;
                }

                cargados.add(new Producto(nombre, precio, categoria));
            }

            productos.setAll(cargados);
            if (!cargados.isEmpty()) {
                mostrarMensaje("Productos cargados desde " + FILE_NAME + ".", true);
            }

        } catch (IOException e) {
            mostrarMensaje("Error al leer productos: " + e.getMessage(), false);
        }
    }

    // ---------- mensajes ----------

    private void mostrarMensaje(String msg, boolean ok) {
        lblMensaje.setText(msg);
        lblMensaje.getStyleClass().removeAll("error", "ok");
        lblMensaje.getStyleClass().add(ok ? "ok" : "error");
    }

    private void limpiarMensaje() {
        lblMensaje.setText("");
        lblMensaje.getStyleClass().removeAll("error", "ok");
    }
}
