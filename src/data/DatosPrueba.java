package data;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class DatosPrueba {

    public static List<Producto> cargar() {

        List<Producto> lista = new ArrayList<>();

        /* ============================================================
           ATRIBUTOS COMUNES
        ============================================================ */
        Atributo tamaño = new Atributo(1, "Tamaño");
        Atributo color = new Atributo(2, "Color");
        Atributo peso = new Atributo(3, "Peso");
        Atributo formato = new Atributo (4, "Formato");

        /* ============================================================
           PRODUCTO 1 — INCIENSO PREMIUM
        ============================================================ */
        Producto incienso = new Producto(
                1,
                "Incienso Premium",
                Categoria.INCIENSO,
                3.50,
                "/icons/incienso_premium.png"
        );

        Variante inc50 = new Variante(1, 3.50);
        inc50.addValor(new ValorAtributo(1, peso, "50g"));

        Variante inc100 = new Variante(2, 6.00);
        inc100.addValor(new ValorAtributo(2, peso, "100g"));

        incienso.addVariante(inc50);
        incienso.addVariante(inc100);

        lista.add(incienso);

        /* ============================================================
           PRODUCTO 2 — FIGURA ÁNGEL
        ============================================================ */
        Producto figura = new Producto(
                2,
                "Figura Ángel",
                Categoria.FIGURA,
                8.00,
                "/icons/angel.png"
        );

        Variante figPeq = new Variante(3, 8.00);
        figPeq.addValor(new ValorAtributo(3, tamaño, "Pequeña"));

        Variante figMed = new Variante(4, 12.00);
        figMed.addValor(new ValorAtributo(4, tamaño, "Mediana"));

        figura.addVariante(figPeq);
        figura.addVariante(figMed);

        lista.add(figura);

        /* ============================================================
           PRODUCTO 3 — LÁMINA DECORATIVA
        ============================================================ */
        Producto lamina = new Producto(
                3,
                "Lámina Decorativa",
                Categoria.LAMINA,
                4.00,
                "/icons/lamina-deco.png"
        );

        Variante lamA4Azul = new Variante(5, 4.00);
        lamA4Azul.addValor(new ValorAtributo(5, tamaño, "A4"));
        lamA4Azul.addValor(new ValorAtributo(6, color, "Azul"));

        Variante lamA3Roja = new Variante(6, 6.00);
        lamA3Roja.addValor(new ValorAtributo(7, tamaño, "A3"));
        lamA3Roja.addValor(new ValorAtributo(8, color, "Roja"));

        lamina.addVariante(lamA4Azul);
        lamina.addVariante(lamA3Roja);

        lista.add(lamina);

        return lista;
    }
}
