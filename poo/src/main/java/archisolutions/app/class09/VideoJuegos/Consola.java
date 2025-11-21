package archisolutions.app.class09.VideoJuegos;

public class Consola {
    private String nombre;
    private TIPO_CONSOLA tipo;

    public Consola(String nombre, TIPO_CONSOLA tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public TIPO_CONSOLA getTipo() {
        return tipo;
    }

    public enum TIPO_CONSOLA {
        PLAYSTATION,
        PC,
        XBOX
    }
}
