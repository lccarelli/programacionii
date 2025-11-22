package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Controller {

    private static final String DEFAULT_FILE_NAME = "contactos.txt";
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private TextField txtFiltro;

    @FXML
    private TableView<Contacto> tblContactos;

    @FXML
    private TableColumn<Contacto, String> colNombre;

    @FXML
    private TableColumn<Contacto, String> colApellido;

    @FXML
    private TableColumn<Contacto, String> colTelefono;

    @FXML
    private TableColumn<Contacto, String> colCreado;

    @FXML
    private TableColumn<Contacto, String> colModificado;

    @FXML
    private TextField txtNombreNuevo;

    @FXML
    private TextField txtApellidoNuevo;

    @FXML
    private TextField txtTelefonoNuevo;

    @FXML
    private Label lblMensaje;

    private ObservableList<Contacto> contactos;
    private FilteredList<Contacto> filteredContactos;

    private File currentFile = new File(DEFAULT_FILE_NAME);

    @FXML
    private void initialize() {
        contactos = FXCollections.observableArrayList();

        // filtro
        filteredContactos = new FilteredList<>(contactos, c -> true);
        SortedList<Contacto> sorted = new SortedList<>(filteredContactos);
        sorted.comparatorProperty().bind(tblContactos.comparatorProperty());
        tblContactos.setItems(sorted);

        tblContactos.setEditable(true);

        // columnas: valueFactory
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        colCreado.setCellValueFactory(cd ->
                new SimpleStringProperty(formatDate(cd.getValue().getFechaCreacion())));
        colModificado.setCellValueFactory(cd ->
                new SimpleStringProperty(formatDate(cd.getValue().getFechaModificacion())));

        // columnas editables
        colNombre.setCellFactory(TextFieldTableCell.forTableColumn());
        colApellido.setCellFactory(TextFieldTableCell.forTableColumn());
        colTelefono.setCellFactory(TextFieldTableCell.forTableColumn());

        colNombre.setOnEditCommit(e -> {
            Contacto c = e.getRowValue();
            c.setNombre(e.getNewValue());
            touchAndSave(c);
        });

        colApellido.setOnEditCommit(e -> {
            Contacto c = e.getRowValue();
            c.setApellido(e.getNewValue());
            touchAndSave(c);
        });

        colTelefono.setOnEditCommit(e -> {
            Contacto c = e.getRowValue();
            c.setTelefono(e.getNewValue());
            touchAndSave(c);
        });

        // filtro en tiempo real
        txtFiltro.textProperty().addListener((obs, oldVal, newVal) -> {
            String filtro = newVal == null ? "" : newVal.trim().toLowerCase();
            filteredContactos.setPredicate(contacto -> {
                if (filtro.isEmpty()) return true;
                String n = safeString(contacto.getNombre()).toLowerCase();
                String a = safeString(contacto.getApellido()).toLowerCase();
                String t = safeString(contacto.getTelefono()).toLowerCase();
                return n.contains(filtro) || a.contains(filtro) || t.contains(filtro);
            });

            if (filteredContactos.isEmpty() && !contactos.isEmpty()) {
                mostrarMensaje("No hay contactos que coincidan con el filtro.", false);
            } else {
                limpiarMensaje();
            }
        });

        // cargar archivo por defecto si existe
        cargarDesdeArchivo();
    }

    // --- acciones UI ---

    @FXML
    private void onSeleccionarArchivo() {
        limpiarMensaje();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Seleccionar archivo de contactos");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de texto", "*.txt", "*.dat"));

        File elegido = chooser.showOpenDialog(tblContactos.getScene().getWindow());
        if (elegido != null) {
            currentFile = elegido;
            cargarDesdeArchivo();
        }
    }

    @FXML
    private void onAgregarNuevo() {
        limpiarMensaje();

        String nombre = safeTrim(txtNombreNuevo.getText());
        String apellido = safeTrim(txtApellidoNuevo.getText());
        String telefono = safeTrim(txtTelefonoNuevo.getText());

        if (nombre.isEmpty() && apellido.isEmpty() && telefono.isEmpty()) {
            mostrarMensaje("Ingresá al menos nombre, apellido o teléfono.", false);
            return;
        }

        LocalDateTime ahora = LocalDateTime.now();
        Contacto c = new Contacto(nombre, apellido, telefono, ahora, ahora);
        contactos.add(c);
        guardarEnArchivo();

        txtNombreNuevo.clear();
        txtApellidoNuevo.clear();
        txtTelefonoNuevo.clear();

        mostrarMensaje("Contacto agregado.", true);
    }

    @FXML
    private void onEliminarSeleccionado() {
        limpiarMensaje();

        Contacto seleccionado = tblContactos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarMensaje("No hay contacto seleccionado para eliminar.", false);
            return;
        }

        contactos.remove(seleccionado);
        guardarEnArchivo();
        mostrarMensaje("Contacto eliminado.", true);
    }

    // --- persistencia TXT ; ---

    private void guardarEnArchivo() {
        if (currentFile == null) {
            currentFile = new File(DEFAULT_FILE_NAME);
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(currentFile, false))) {
            // encabezado
            pw.println("nombre;apellido;telefono;creado;modificado");

            for (Contacto c : contactos) {
                String linea = String.format(Locale.US, "%s;%s;%s;%s;%s",
                        sanitize(c.getNombre()),
                        sanitize(c.getApellido()),
                        sanitize(c.getTelefono()),
                        formatDate(c.getFechaCreacion()),
                        formatDate(c.getFechaModificacion()));

                pw.println(linea);
            }

        } catch (IOException e) {
            mostrarMensaje("Error al guardar contactos: " + e.getMessage(), false);
        }
    }

    private void cargarDesdeArchivo() {
        contactos.clear();

        if (currentFile == null || !currentFile.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(currentFile))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // saltar encabezado
                if (primera && linea.toLowerCase().startsWith("nombre;")) {
                    primera = false;
                    continue;
                }
                primera = false;

                String[] partes = linea.split(";");
                if (partes.length < 5) continue;

                String nombre = partes[0].trim();
                String apellido = partes[1].trim();
                String telefono = partes[2].trim();
                String creadoStr = partes[3].trim();
                String modifStr = partes[4].trim();

                LocalDateTime creado = parseDate(creadoStr);
                LocalDateTime modif = parseDate(modifStr);

                contactos.add(new Contacto(nombre, apellido, telefono, creado, modif));
            }

            if (!contactos.isEmpty()) {
                mostrarMensaje("Contactos cargados desde " + currentFile.getName() + ".", true);
            }

        } catch (IOException e) {
            mostrarMensaje("Error al leer contactos: " + e.getMessage(), false);
        }
    }

    // --- helpers ---

    private void touchAndSave(Contacto c) {
        c.setFechaModificacion(LocalDateTime.now());
        tblContactos.refresh();
        guardarEnArchivo();
        mostrarMensaje("Contacto actualizado.", true);
    }

    private String formatDate(LocalDateTime dt) {
        if (dt == null) return "";
        return dt.format(DATE_FORMAT);
    }

    private LocalDateTime parseDate(String s) {
        if (s == null || s.isEmpty()) return LocalDateTime.now();
        try {
            return LocalDateTime.parse(s, DATE_FORMAT);
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }

    private String sanitize(String s) {
        return safeString(s).replace(";", ",")
                            .replace("\n", " ")
                            .replace("\r", " ");
    }

    private String safeString(String s) {
        return s == null ? "" : s;
    }

    private String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

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
