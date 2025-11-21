package archisolutions.app.class07.SistemaPedidosTienda;

public class Producto {
    private int id;
    private String name;
    private double precio;
    private int stock;

    public Producto(String name, double precio, int stock){
        this.name = name;
        this.precio = precio;
        this.stock = stock;
    }

   public boolean restarStock(int cantidad){
        if(existeStock(cantidad)){
            this.stock = this.stock - cantidad;
            return true;
        }
       return false;
   }

   private boolean existeStock(int cantidad){
        return this.stock > cantidad;
   }

   public double getPrecio(){
       return this.precio;
   }

}
