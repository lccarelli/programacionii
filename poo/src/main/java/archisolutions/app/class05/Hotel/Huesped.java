package archisolutions.app.class05.Hotel;

public class Huesped {
    private final String nombre;
    private final String documentoIdentidad;

    Huesped(String nombre, String documentoIdentidad){
        this.documentoIdentidad = documentoIdentidad;
        this.nombre = nombre;
    }

    public String mostrarDatos(){
        return  "Huesped: Nombre: " + this.nombre +
                " (DNI: " + this.documentoIdentidad + ")";
    }
}
