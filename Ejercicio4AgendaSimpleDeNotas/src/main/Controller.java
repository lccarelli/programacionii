package main;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;

public class Controller {

    private static final String NOTES_DIR_NAME = "notas"; // carpeta
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private TextField txtTitulo;

    @FXML
    private TextArea txtContenido;

    @FXML
    private DatePicker dpFecha;

    @FXML
    private Label lblEstado;

    @FXML
    private ListView<String> lstNotas;

    private File notesDir;
    private File notaSeleccionada;

    @FXML
    private void initialize() {
        notesDir = new File(NOTES_DIR_NAME);
        if (!notesDir.exists()) {
            notesDir.mkdirs();
        }
        
        dpFecha.setValue(LocalDate.now()); 
        cargarListaNotas();
    }

    @FXML
    private void onGuardar() {
        limpiarClasesEstado();

        String titulo = txtTitulo.getText() != null ? txtTitulo.getText().trim() : "";
        String contenido = txtContenido.getText() != null ? txtContenido.getText().trim() : "";

        if (titulo.isEmpty()) {
            lblEstado.setText("El título no puede estar vacío.");
            lblEstado.getStyleClass().add("error");
            return;
        }

        LocalDate fecha = LocalDate.now();
        dpFecha.setValue(fecha);

        // si hay una nota seleccionada, se sobrescribe; si no, se crea un archivo nuevo
        File archivo;
        if (notaSeleccionada != null) {
            archivo = notaSeleccionada;
        } else {
            archivo = generarArchivoParaTitulo(titulo);
        }

        if (guardarNotaEnArchivo(archivo, titulo, contenido, fecha)) {
            lblEstado.setText("Nota guardada en " + archivo.getName());
            lblEstado.getStyleClass().add("ok");
            notaSeleccionada = archivo;

            cargarListaNotas();
            lstNotas.getSelectionModel().select(archivo.getName());

            // después de guardar: limpiar título y contenido
            txtTitulo.clear();
            txtContenido.clear();
            // dejar la fecha en hoy para la próxima nota
            dpFecha.setValue(LocalDate.now());
            // opcional: dejar de tener una nota seleccionada
            notaSeleccionada = null;
        } else {
            lblEstado.setText("Error al guardar la nota.");
            lblEstado.getStyleClass().add("error");
        }
    }


    @FXML
    private void onLimpiar() {
        limpiarClasesEstado();
        txtTitulo.clear();
        txtContenido.clear();
        dpFecha.setValue(null);
        notaSeleccionada = null;
        lblEstado.setText("Campos limpiados. Puede crear una nueva nota.");
        lblEstado.getStyleClass().add("ok");
    }

    @FXML
    private void onSeleccionarNota() {
        String fileName = lstNotas.getSelectionModel().getSelectedItem();
        if (fileName == null) {
            return;
        }

        File archivo = new File(notesDir, fileName);
        if (!archivo.exists()) {
            lblEstado.setText("El archivo seleccionado no existe.");
            lblEstado.getStyleClass().add("error");
            cargarListaNotas();
            return;
        }

        if (cargarNotaDesdeArchivo(archivo)) {
            notaSeleccionada = archivo;
            lblEstado.setText("Nota cargada desde " + archivo.getName());
            lblEstado.getStyleClass().add("ok");
        } else {
            lblEstado.setText("No se pudo leer la nota seleccionada.");
            lblEstado.getStyleClass().add("error");
        }
    }

    // ------------------ MÉTODOS PRIVADOS ----------------------

    private void cargarListaNotas() {
        lstNotas.getItems().clear();

        File[] archivos = notesDir.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".txt"));

        if (archivos != null && archivos.length > 0) {
            // ordenamos por fecha de modificación (más recientes primero)
            Arrays.sort(archivos, Comparator.comparingLong(File::lastModified).reversed());

            for (File f : archivos) {
                lstNotas.getItems().add(f.getName());
            }
        }
    }

    private File generarArchivoParaTitulo(String titulo) {
        String baseName = titulo.trim()
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "") // solo letras/números/espacios
                .replaceAll("\\s+", "_");

        if (baseName.isEmpty()) {
            baseName = "nota";
        }

        File file = new File(notesDir, baseName + ".txt");
        int suffix = 1;
        while (file.exists()) {
            file = new File(notesDir, baseName + "_" + suffix + ".txt");
            suffix++;
        }
        return file;
    }

    private boolean guardarNotaEnArchivo(File archivo, String titulo,
                                         String contenido, LocalDate fecha) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(archivo, false); // sobrescribe
            bw = new BufferedWriter(fw);

            // línea 1: título
            bw.write(titulo);
            bw.newLine();

            // línea 2: fecha
            bw.write(fecha.format(DATE_FORMAT));
            bw.newLine();

            // resto: contenido (puede ser multi-línea)
            if (contenido != null && !contenido.isEmpty()) {
                bw.write(contenido);
            }

            bw.newLine();
            bw.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Error al escribir archivo " + archivo.getName() + ": " + e.getMessage());
            return false;
        } finally {
            try {
                if (bw != null) bw.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo: " + e.getMessage());
            }
        }
    }

    private boolean cargarNotaDesdeArchivo(File archivo) {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String titulo = br.readLine();         // línea 1
            String fechaStr = br.readLine();       // línea 2

            StringBuilder contenido = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                if (contenido.length() > 0) {
                    contenido.append(System.lineSeparator());
                }
                contenido.append(linea);
            }

            txtTitulo.setText(titulo != null ? titulo : "");
            if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                try {
                    dpFecha.setValue(LocalDate.parse(fechaStr.trim(), DATE_FORMAT));
                } catch (Exception e) {
                    dpFecha.setValue(null);
                }
            } else {
                dpFecha.setValue(null);
            }
            txtContenido.setText(contenido.toString());

            return true;

        } catch (IOException e) {
            System.out.println("Error al leer archivo " + archivo.getName() + ": " + e.getMessage());
            return false;
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar archivo: " + e.getMessage());
            }
        }
    }

    private void limpiarClasesEstado() {
        lblEstado.getStyleClass().removeAll("error", "ok");
    }
}
