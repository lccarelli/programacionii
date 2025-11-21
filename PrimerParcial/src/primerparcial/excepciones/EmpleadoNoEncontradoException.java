package primerparcial.excepciones;

public class EmpleadoNoEncontradoException extends RuntimeException {
    public EmpleadoNoEncontradoException() {
        super("Empleado no encontrado.");
    }
}
