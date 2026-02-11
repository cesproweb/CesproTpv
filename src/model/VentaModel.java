package model;

import java.util.ArrayList;
import java.util.List;

public class VentaModel {

    private List<VentaItem> carrito;

    public VentaModel() {
        carrito = new ArrayList<>();
    }

    public List<VentaItem> getCarrito() {
        return carrito;
    }

    public void addItem(VentaItem item) {
        carrito.add(item);
    }

    public void removeItem(VentaItem item) {
        carrito.remove(item);
    }

    public double getSubtotal() {
        return carrito.stream().mapToDouble(VentaItem::getSubtotal).sum();
    }

    public double getIVA() {
        return getSubtotal() * 0.21; // IVA 21%
    }

    public double getTotal() {
        return getSubtotal() + getIVA();
    }

    public void clear() {
        carrito.clear();
    }
}
