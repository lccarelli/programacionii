package archisolutions.app.excepciones;

public class OperacionInvalidaException extends IllegalArgumentException {
    public OperacionInvalidaException() {
        super("No se le puede calcular sueldo a un alumno");
    }
}
