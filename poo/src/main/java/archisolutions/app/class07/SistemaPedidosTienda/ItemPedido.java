package archisolutions.app.class07.SistemaPedidosTienda;

public class ItemPedido {
    private Producto producto;
    private int cantidad;
    private double subtotal;

    public ItemPedido(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void calcularSubtotal(){
        this.subtotal = this.producto.getPrecio() * this.cantidad;
    }
}
