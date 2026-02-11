package model;

import java.util.ArrayList;
import java.util.List;

public class Producto {

    private int id;
    private String nombre;
    private Categoria categoria;
    private double precioBase;
    private String imagen;

    private List<Variante> variantes;

    public Producto(int id, String nombre, Categoria categoria, double precioBase, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.imagen = imagen;
        this.variantes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public String getImagen() {
        return imagen;
    }

    public List<Variante> getVariantes() {
        return variantes;
    }

    public void addVariante(Variante v) {
        variantes.add(v);
    }

    public boolean tieneVariantes() {
        return !variantes.isEmpty();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
