package archisolutions.app.excepciones;

public class DniDuplicadoException extends RuntimeException {
    public DniDuplicadoException() {
        super("Ya existe una persona con ese dni");
    }
}
