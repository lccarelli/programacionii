package main;

import java.time.LocalDate;

public class Tarea {

    private String nombre;
    private boolean hecha;
    private LocalDate fechaCreacion;
    private LocalDate fechaEstimada;

    public Tarea(String nombre, boolean hecha,
                 LocalDate fechaCreacion, LocalDate fechaEstimada) {
        this.nombre = nombre;
        this.hecha = hecha;
        this.fechaCreacion = fechaCreacion;
        this.fechaEstimada = fechaEstimada;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isHecha() {
        return hecha;
    }

    public void setHecha(boolean hecha) {
        this.hecha = hecha;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDate fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }
}
