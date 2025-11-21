package archisolutions.app.class07.SistemaPedidosTienda;

import java.util.List;

public class Tienda {
    private List<Pedido> pedidos;
    private List<Producto> productos;
    private List<Cliente> clientes;
    private static double DESCUENTO_10_PORCIENTO = 0.10;
    private static double DESCUENTO_15_PORCIENTO = 0.15;
    private static double MONTO_MINIMO_DESCUENTO = 50000;
    private static int CANTIDAD_MINIMA_PRODUCTOS = 5;


    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public String buscarPedido(int id){
        return this.pedidos.get(id).mostrarDetalle();
    }

    public void vender(Pedido pedido){

        this.pedidos.add(pedido);
    }
}
