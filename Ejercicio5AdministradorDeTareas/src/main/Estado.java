/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

public enum Estado {
    PENDIENTE,
    EN_PROGRESO,
    HECHA;

    public static Estado fromString(String valor) {
        if (valor == null) return PENDIENTE;
        return switch (valor.trim().toUpperCase()) {
            case "HECHA" -> HECHA;
            case "EN_PROGRESO" -> EN_PROGRESO;
            default -> PENDIENTE;
        };
    }

    public String toDisplay() {
        return switch (this) {
            case HECHA -> "Hecha";
            case EN_PROGRESO -> "En progreso";
            default -> "Pendiente";
        };
    }

    public String icon() {
        return switch (this) {
            case HECHA -> "✅";
            case EN_PROGRESO -> "⏳";
            default -> "📝";
        };
    }
}

