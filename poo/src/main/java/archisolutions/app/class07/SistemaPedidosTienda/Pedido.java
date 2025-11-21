package archisolutions.app.class07.SistemaPedidosTienda;

import java.util.List;

public class Pedido {
    private int numero;
    private List<ItemPedido> items;
    private Cliente cliente;
    private double total;
    private double descuento;
    private double subtotal;
    private static int CONTADOR;

    public Pedido(List<ItemPedido> items, Cliente cliente){
        this.numero = asignarNumeroId();
        this.items = items;
        this.cliente = cliente;

    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private int asignarNumeroId(){
        return CONTADOR++;
    }

    public void calcularSubTotal(){
        this.subtotal = 0;
        for(ItemPedido item : this.items){
            item.calcularSubtotal();
            this.subtotal += item.getSubtotal();
        }
    }

    public void aplicarDescuentoPorMonto(double descuento, double montoMinimo){
        if(this.total > montoMinimo){
            this.descuento = this.total * descuento;
        }
    }

    public void aplicarDescuentoPorCantidadProducto(double descuento, int cantidadMinima){
        for(ItemPedido item : this.items){
            if(item.getCantidad() >= cantidadMinima){
                this.descuento = item.getSubtotal() * descuento;
            }
        }
    }

    public String mostrarDetalle(){
        StringBuilder sb = new StringBuilder();
        sb.append("Detalle de pedido:");
        sb.append("\nCliente: " + this.cliente.getNombre());
        sb.append("\nNúmero de pedido: " + this.numero);
        sb.append("\nItems:");
        for(ItemPedido item : this.items){
            sb.append("\nProducto: "+ item.getProducto());
            sb.append("Cantidad: " + item.getCantidad());
            sb.append("Subtotal: " + item.getSubtotal());
        }

        return sb.toString();
    }

}
