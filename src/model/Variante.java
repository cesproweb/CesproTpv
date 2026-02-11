package model;

import java.util.ArrayList;
import java.util.List;

public class Variante {

    private int id;
    private double precio;
    private List<ValorAtributo> valores;

    public Variante(int id, double precio) {
        this.id = id;
        this.precio = precio;
        this.valores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public List<ValorAtributo> getValores() {
        return valores;
    }

    public void addValor(ValorAtributo valor) {
        valores.add(valor);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ValorAtributo v : valores) {
            sb.append(v.getAtributo().getNombre())
              .append(": ")
              .append(v.getValor())
              .append(" | ");
        }
        return sb.toString();
    }
}
