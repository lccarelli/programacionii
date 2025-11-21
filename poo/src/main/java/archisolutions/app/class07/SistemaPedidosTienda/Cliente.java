package archisolutions.app.class07.SistemaPedidosTienda;

public class Cliente {
    private String nombre;
    private String email;
    private final int id;
    private static int CONTADOR;

    public Cliente(String nombre, String email){
        this.nombre = nombre;
        this.email = email;
        this.id = asignarNumeroId();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    private int asignarNumeroId(){
        return CONTADOR++;
    }
}
