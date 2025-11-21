package archisolutions.app.class08.ParqueKarting;

public class Karting {
    private int numero;
    private static int CONTADOR = 0;
    private Estado estado;

    public Karting(){
        this.numero = asignarNumero();
        this.estado = Estado.DISPONIBLE;
    }

    private int asignarNumero() {
        return CONTADOR++;
    }


    public enum Estado{
        OCUPADO,
        DISPONIBLE
    }

}
