package archisolutions.app.class05.Hotel;

public class Habitacion {

    private int numero;
    private Tipo tipo;
    private Estado estado;

    Habitacion(int numero, String tipo){
        this.numero = numero;
        setHabitacionTipo(tipo);
        this.estado = Estado.LIBRE;
    }

    //-------- private ---------//

    private void setHabitacionTipo(String tipo){
        if(!tipo.isEmpty()){
            this.tipo = Tipo.valueOf(tipo);
        }
    }


    //-------- public ---------//
    public Estado estado(){
        return this.estado;
    }

    public enum Estado {
       LIBRE,
        OCUPADA
    }

    public enum Tipo {
        SIMPLE,
        DOBLE,
        SUIT
    }

    public void ocupar(){
        this.estado = Estado.OCUPADA;
    }

    public void liberar(){
        this.estado = Estado.LIBRE;
    }

    public String mostrarInfo(){
        StringBuilder sb = new StringBuilder();
        sb.append("Habitacion Nro:").append(this.numero);
        sb.append(" - Tipo: ").append(this.tipo);
        sb.append(" - Estado: ").append(this.estado);
        return sb.toString();
    }

}
