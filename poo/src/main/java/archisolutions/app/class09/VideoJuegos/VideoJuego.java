package archisolutions.app.class09.VideoJuegos;

import java.util.List;

public class VideoJuego {
    private String titulo;
    private int capacidadGigabyte;
    private List<Consola> consolasCompatibles;


    public VideoJuego(String titulo, int capacidadGigabyte) {
        this.titulo = titulo;
        this.capacidadGigabyte = capacidadGigabyte;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getCapacidadGigabyte() {
        return capacidadGigabyte;
    }


}
