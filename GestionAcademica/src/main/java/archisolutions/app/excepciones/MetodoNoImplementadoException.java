package archisolutions.app.excepciones;

public class MetodoNoImplementadoException extends RuntimeException {
    public MetodoNoImplementadoException() {
        super("Este metodo no se encuentra implementado.");
    }
}
