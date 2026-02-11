package model;

public class VentaItem {

    private Producto producto;
    private Variante variante;     // puede ser null si no hay variantes
    private int cantidad;
    private double precioUnitario; // precio base o precio de variante

    // DESCUENTO EN PORCENTAJE (0–100)
    private double descuentoPorcentaje;

    public VentaItem(Producto producto, Variante variante, int cantidad) {
        this.producto = producto;
        this.variante = variante;
        this.cantidad = cantidad;

        // Si hay variante, usamos su precio; si no, el precio base del producto
        if (variante != null) {
            this.precioUnitario = variante.getPrecio();
        } else {
            this.precioUnitario = producto.getPrecioBase();
        }

        this.descuentoPorcentaje = 0;
    }

    // ============================================================
    // GETTERS
    // ============================================================

    public Producto getProducto() {
        return producto;
    }

    public Variante getVariante() {
        return variante;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    // ============================================================
    // SETTERS
    // ============================================================

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setDescuentoPorcentaje(double descuentoPorcentaje) {
        if (descuentoPorcentaje < 0) descuentoPorcentaje = 0;
        if (descuentoPorcentaje > 100) descuentoPorcentaje = 100;
        this.descuentoPorcentaje = descuentoPorcentaje;
    }

    // ============================================================
    // CÁLCULO DEL SUBTOTAL
    // ============================================================

    public double getSubtotal() {
        double factor = 1 - (descuentoPorcentaje / 100.0);
        if (factor < 0) factor = 0;

        return precioUnitario * factor * cantidad;
    }

    @Override
    public String toString() {
        if (variante != null) {
            return producto.getNombre() + " (" + variante.toString() + ")";
        }
        return producto.getNombre();
    }
}
