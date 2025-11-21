package archisolutions.app.class05.Hotel;

public class Reserva {
    private Habitacion habitacion;
    private Huesped huesped;

    Reserva(Habitacion habitacion, Huesped huesped){
        this.habitacion = habitacion;
        this.huesped = huesped;
    }

    public void confirmar(){
        if(Habitacion.Estado.LIBRE.equals(this.habitacion.estado()))
        this.habitacion.ocupar();
        this.mensajeConfirmacion();

        if (this.habitacion.estado().equals(Habitacion.Estado.OCUPADA)){
            this.mensajeErrorEnConfirmacion();
        }
    }

    public void finalizar(){
        this.habitacion.liberar();
    }

    private void mensajeConfirmacion(){

        StringBuilder sb = new StringBuilder();
        sb.append("Reserva confirmada:");
        sb.append(this.huesped.mostrarDatos());
        sb.append(this.habitacion.mostrarInfo());

        System.out.println(sb.toString());
    }

    private void mensajeErrorEnConfirmacion(){
        System.out.println("No se pudo realizar la reserva.");
    }

}
