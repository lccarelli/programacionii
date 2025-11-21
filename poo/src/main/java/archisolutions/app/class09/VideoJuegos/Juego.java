package archisolutions.app.class09.VideoJuegos;

import java.util.List;

public class Juego {
    private String titulo;
    private int capacidadGigabyte;
    private List<Consola> consolasCompatibles;

    public Juego(String titulo, int capacidadGigabyte) {
        this.titulo = titulo;
        this.capacidadGigabyte = capacidadGigabyte;
    }

    public String getTitulo() {
        return titulo;
    }

}
