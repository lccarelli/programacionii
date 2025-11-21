package archisolutions.app.excepciones;

public class PersonaNoEncontradaException extends RuntimeException {
    public PersonaNoEncontradaException() {
        super("Esta persona no se encuentra dada de alta.");
    }
}
