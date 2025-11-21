package primerparcial.excepciones;

public class OperacionNoImplementadaException extends RuntimeException {
    public OperacionNoImplementadaException() {
        super("Esta operación no está implementada.");
    }
}
