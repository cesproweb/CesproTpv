package model;

public class ValorAtributo {

    private int id;
    private Atributo atributo;
    private String valor;

    public ValorAtributo(int id, Atributo atributo, String valor) {
        this.id = id;
        this.atributo = atributo;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public Atributo getAtributo() {
        return atributo;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return atributo.getNombre() + ": " + valor;
    }
}
