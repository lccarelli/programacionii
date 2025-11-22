package main;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    private static final String FILE_NAME = "tareas.txt";
    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private TextField txtNombre;

    @FXML
    private DatePicker dpEstimado;

    @FXML
    private TableView<Tarea> tblTareas;

    @FXML
    private TableColumn<Tarea, Boolean> colHecha;

    @FXML
    private TableColumn<Tarea, String> colNombre;

    @FXML
    private TableColumn<Tarea, String> colCreada;

    @FXML
    private TableColumn<Tarea, String> colEstimado;

    @FXML
    private Label lblMensaje;

    private ObservableList<Tarea> tareas;

    @FXML
    private void initialize() {
        tareas = FXCollections.observableArrayList();
        tblTareas.setItems(tareas);

        // columnas
        colNombre.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getNombre()));

        colCreada.setCellValueFactory(data -> {
            LocalDate d = data.getValue().getFechaCreacion();
            return new SimpleStringProperty(d != null ? d.format(DATE_FMT) : "");
        });

        colEstimado.setCellValueFactory(data -> {
            LocalDate d = data.getValue().getFechaEstimada();
            return new SimpleStringProperty(d != null ? d.format(DATE_FMT) : "");
        });

        // columna de checkbox
        colHecha.setCellValueFactory(data ->
                new SimpleBooleanProperty(data.getValue().isHecha()));

        colHecha.setCellFactory(col -> new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(e -> {
                    Tarea t = getTableRow().getItem();
                    if (t != null) {
                        t.setHecha(checkBox.isSelected());
                        tblTareas.refresh(); // para que se actualice el color de la fila
                        guardarEnArchivo();
                        mostrarMensaje("Estado actualizado.", true);
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Tarea t = getTableView().getItems().get(getIndex());
                    checkBox.setSelected(t.isHecha());
                    setGraphic(checkBox);
                }
            }
        });

        // colores por fila según estado
        tblTareas.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Tarea tarea, boolean empty) {
                super.updateItem(tarea, empty);
                getStyleClass().removeAll("row-pending", "row-delayed", "row-done");

                if (empty || tarea == null) {
                    return;
                }

                LocalDate hoy = LocalDate.now();

                if (tarea.isHecha()) {
                    getStyleClass().add("row-done");
                } else if (tarea.getFechaEstimada() != null
                        && hoy.isAfter(tarea.getFechaEstimada())) {
                    getStyleClass().add("row-delayed");
                } else {
                    getStyleClass().add("row-pending");
                }
            }
        });

        // fecha estimada por defecto: hoy
        dpEstimado.setValue(LocalDate.now());

        cargarDesdeArchivo();
    }

    @FXML
    private void onAgregar() {
        limpiarMensaje();

        String nombre = txtNombre.getText() != null ? txtNombre.getText().trim() : "";
        LocalDate estimada = dpEstimado.getValue();

        if (nombre.isEmpty()) {
            mostrarMensaje("El nombre de la tarea no puede estar vacío.", false);
            return;
        }
        if (estimada == null) {
            mostrarMensaje("Debe seleccionar una fecha estimada.", false);
            return;
        }

        LocalDate hoy = LocalDate.now();
        Tarea t = new Tarea(nombre, false, hoy, estimada);
        tareas.add(t);

        guardarEnArchivo();

        txtNombre.clear();
        dpEstimado.setValue(LocalDate.now());

        mostrarMensaje("Tarea agregada.", true);
    }

    @FXML
    private void onEliminar() {
        limpiarMensaje();

        Tarea seleccionada = tblTareas.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarMensaje("No hay tarea seleccionada para eliminar.", false);
            return;
        }

        tareas.remove(seleccionada);
        guardarEnArchivo();
        mostrarMensaje("Tarea eliminada.", true);
    }

    // ---------- Persistencia ----------

    private void guardarEnArchivo() {
        File archivo = new File(FILE_NAME);
        BufferedWriter bw = null;

        try {
            FileWriter fw = new FileWriter(archivo, false);
            bw = new BufferedWriter(fw);

            for (Tarea t : tareas) {
                String linea = t.getNombre() + ";"
                        + (t.isHecha() ? "1" : "0") + ";"
                        + (t.getFechaCreacion() != null ? t.getFechaCreacion().format(DATE_FMT) : "") + ";"
                        + (t.getFechaEstimada() != null ? t.getFechaEstimada().format(DATE_FMT) : "");
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            mostrarMensaje("Error al guardar archivo: " + e.getMessage(), false);
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                // log
            }
        }
    }

    private void cargarDesdeArchivo() {
        File archivo = new File(FILE_NAME);
        if (!archivo.exists()) {
            return;
        }

        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            tareas.clear();

            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] partes = linea.split(";");
                String nombre = partes.length > 0 ? partes[0].trim() : "";
                boolean hecha = partes.length > 1 && partes[1].trim().equals("1");

                LocalDate creada = null;
                LocalDate estimada = null;

                if (partes.length > 2 && !partes[2].trim().isEmpty()) {
                    creada = LocalDate.parse(partes[2].trim(), DATE_FMT);
                }
                if (partes.length > 3 && !partes[3].trim().isEmpty()) {
                    estimada = LocalDate.parse(partes[3].trim(), DATE_FMT);
                }

                if (!nombre.isEmpty()) {
                    tareas.add(new Tarea(nombre, hecha, creada, estimada));
                }
            }

        } catch (IOException e) {
            mostrarMensaje("Error al leer archivo: " + e.getMessage(), false);
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                // log
            }
        }
    }

    // ---------- Mensajes ----------

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
