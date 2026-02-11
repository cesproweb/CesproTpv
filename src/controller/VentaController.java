package controller;

import model.Producto;
import model.Variante;
import model.VentaItem;

import java.util.ArrayList;
import java.util.List;

public class VentaController {

    private final List<Producto> productos;
    private final List<VentaItem> carrito;

    public VentaController(List<Producto> productos) {
        this.productos = productos;
        this.carrito = new ArrayList<>();
    }

    // ============================================================
    // PRODUCTOS DISPONIBLES
    // ============================================================

    public List<Producto> getProductos() {
        return productos;
    }

    // ============================================================
    // CARRITO
    // ============================================================

    public List<VentaItem> getCarrito() {
        return carrito;
    }

    // ============================================================
    // AÑADIR PRODUCTO
    // ============================================================

    public void addProducto(Producto producto, Variante variante) {

        for (VentaItem item : carrito) {
            if (item.getProducto().getId() == producto.getId()) {

                boolean mismaVariante =
                        (item.getVariante() == null && variante == null) ||
                        (item.getVariante() != null && variante != null &&
                         item.getVariante().getId() == variante.getId());

                if (mismaVariante) {
                    item.setCantidad(item.getCantidad() + 1);
                    return;
                }
            }
        }

        carrito.add(new VentaItem(producto, variante, 1));
    }

    // ============================================================
    // ELIMINAR ITEM
    // ============================================================

    public void eliminarItem(VentaItem item) {
        carrito.remove(item);
    }

    // ============================================================
    // DESCUENTO EN PORCENTAJE
    // ============================================================

    public void aplicarDescuentoPorcentaje(VentaItem item, double porcentaje) {
        item.setDescuentoPorcentaje(porcentaje);
    }

    // ============================================================
    // TOTAL
    // ============================================================

    public double getTotal() {
        double total = 0;

        for (VentaItem item : carrito) {
            total += item.getSubtotal();
        }

        return total;
    }

    // ============================================================
    // LIMPIAR CARRITO
    // ============================================================

    public void limpiarCarrito() {
        carrito.clear();
    }
}
