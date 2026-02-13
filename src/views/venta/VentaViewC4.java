package views.venta;

import controller.VentaController;
import model.Categoria;
import model.Producto;
import model.Variante;
import model.VentaItem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class VentaViewC4 extends BorderPane {

    private final VentaController controller;

    private FlowPane panelCategorias;
    private FlowPane panelProductos;
    private VBox panelCarrito;

    private Label lblSubtotal;
    private Label lblIVA;
    private Label lblTotal;

    public VentaViewC4(VentaController controller) {
        this.controller = controller;
        construirUI();
    }

    private void construirUI() {

        /* ============================================================
           CATEGORÍAS COMO BOTONES GRANDES ARRIBA
        ============================================================ */
        panelCategorias = new FlowPane();
        panelCategorias.setPadding(new Insets(10));
        panelCategorias.setHgap(15);
        panelCategorias.setVgap(10);
        panelCategorias.setAlignment(Pos.CENTER);
        panelCategorias.setStyle("-fx-background-color: #eaeaea; -fx-border-color: #ccc;");

        for (Categoria cat : Categoria.values()) {
            Button btn = new Button(cat.name());
            btn.setStyle("""
                -fx-font-size: 20px;
                -fx-background-color: #ffffff;
                -fx-border-color: #999;
                -fx-padding: 15 25;
                """);
            btn.setOnAction(e -> filtrarPorCategoria(cat));
            panelCategorias.getChildren().add(btn);
        }

        setTop(panelCategorias);

        /* ============================================================
           PRODUCTOS COMO BOTONES GRANDES (CENTRO)
        ============================================================ */
        panelProductos = new FlowPane();
        panelProductos.setPadding(new Insets(20));
        panelProductos.setHgap(25);
        panelProductos.setVgap(25);
        panelProductos.setPrefWrapLength(900);
        panelProductos.setAlignment(Pos.TOP_LEFT);

        cargarBotonesProductos(controller.getProductos());

        ScrollPane scrollProductos = new ScrollPane(panelProductos);
        scrollProductos.setFitToWidth(true);

        setCenter(scrollProductos);

        /* ============================================================
           CARRITO A LA DERECHA (ANCHO)
        ============================================================ */
        panelCarrito = new VBox(15);
        panelCarrito.setPadding(new Insets(15));
        panelCarrito.setPrefWidth(600);
        panelCarrito.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc;");

        construirCarrito();

        setRight(panelCarrito);
    }

    /* ============================================================
       CARGAR BOTONES GRANDES DE PRODUCTOS
    ============================================================ */
    private void cargarBotonesProductos(java.util.List<Producto> productos) {
        panelProductos.getChildren().clear();

        for (Producto p : productos) {

            Button btn = new Button(p.getNombre());
            btn.setPrefSize(220, 120);
            btn.setWrapText(true);

            btn.setStyle("""
                -fx-font-size: 20px;
                -fx-background-color: #ffffff;
                -fx-border-color: #888;
                -fx-border-width: 2;
                -fx-padding: 15;
                """);

            btn.setOnAction(e -> seleccionarProducto(p));

            panelProductos.getChildren().add(btn);
        }
    }

    /* ============================================================
       SELECCIONAR PRODUCTO (VARIANTES)
    ============================================================ */
    private void seleccionarProducto(Producto p) {

        Variante v = null;

        if (!p.getVariantes().isEmpty()) {
            ChoiceDialog<Variante> dialog = new ChoiceDialog<>(p.getVariantes().get(0), p.getVariantes());
            dialog.setTitle("Seleccionar variante");
            dialog.setHeaderText("Elige tamaño/peso/color");
            v = dialog.showAndWait().orElse(null);
        }

        controller.addProducto(p, v);
        actualizarCarrito();
    }

    /* ============================================================
       FILTRAR POR CATEGORÍA
    ============================================================ */
    private void filtrarPorCategoria(Categoria categoria) {
        var filtrados = controller.getProductos().stream()
                .filter(p -> p.getCategoria() == categoria)
                .toList();

        cargarBotonesProductos(filtrados);
    }

    /* ============================================================
       PANEL CARRITO
    ============================================================ */
    private void construirCarrito() {

        Label titulo = new Label("Carrito");
        titulo.setFont(Font.font(26));

        VBox listaItems = new VBox(15);
        listaItems.setPrefHeight(600);

        ScrollPane scrollItems = new ScrollPane(listaItems);
        scrollItems.setFitToWidth(true);

        lblSubtotal = new Label();
        lblIVA = new Label();
        lblTotal = new Label();

        lblSubtotal.setFont(Font.font(20));
        lblIVA.setFont(Font.font(20));
        lblTotal.setFont(Font.font(24));

        Button btnCobrar = new Button("COBRAR");
        btnCobrar.setMaxWidth(Double.MAX_VALUE);
        btnCobrar.setStyle("""
            -fx-font-size: 26px;
            -fx-background-color: #4CAF50;
            -fx-text-fill: white;
            -fx-padding: 20;
            """);

        Button btnVaciar = new Button("VACIAR");
        btnVaciar.setMaxWidth(Double.MAX_VALUE);
        btnVaciar.setStyle("""
            -fx-font-size: 22px;
            -fx-background-color: #d9534f;
            -fx-text-fill: white;
            -fx-padding: 15;
            """);

        btnVaciar.setOnAction(e -> {
            controller.limpiarCarrito();
            actualizarCarrito();
        });

        panelCarrito.getChildren().addAll(
                titulo,
                scrollItems,
                lblSubtotal,
                lblIVA,
                lblTotal,
                btnCobrar,
                btnVaciar
        );

        actualizarCarrito();
    }

    /* ============================================================
       ACTUALIZAR CARRITO
    ============================================================ */
    private void actualizarCarrito() {

        VBox listaItems = (VBox) ((ScrollPane) panelCarrito.getChildren().get(1)).getContent();
        listaItems.getChildren().clear();

        for (VentaItem item : controller.getCarrito()) {

            VBox caja = new VBox(5);
            caja.setPadding(new Insets(10));
            caja.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 2;");

            Label lblNombre = new Label(item.getProducto().getNombre());
            lblNombre.setFont(Font.font(20));

            Label lblVariante = new Label(
                    item.getVariante() != null ? item.getVariante().toString() : ""
            );
            lblVariante.setStyle("-fx-text-fill: #666; -fx-font-size: 16px;");

            HBox controles = new HBox(10);
            controles.setAlignment(Pos.CENTER_LEFT);

            Button btnMas = new Button("+");
            Button btnMenos = new Button("-");
            Button btnEliminar = new Button("X");
            Button btnDescuento = new Button("%");

            btnMas.setStyle("-fx-font-size: 20px;");
            btnMenos.setStyle("-fx-font-size: 20px;");
            btnEliminar.setStyle("-fx-font-size: 20px;");
            btnDescuento.setStyle("-fx-font-size: 20px; -fx-background-color: #FF9800; -fx-text-fill: white;");

            btnDescuento.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog(
                        String.valueOf(item.getDescuentoPorcentaje())
                );
                dialog.setTitle("Aplicar descuento");
                dialog.setHeaderText("Descuento para " + item.getProducto().getNombre());
                dialog.setContentText("Introduce descuento en %:");

                dialog.showAndWait().ifPresent(valor -> {
                    try {
                        double porcentaje = Double.parseDouble(valor);
                        if (porcentaje < 0) porcentaje = 0;
                        if (porcentaje > 100) porcentaje = 100;

                        controller.aplicarDescuentoPorcentaje(item, porcentaje);
                        actualizarCarrito();

                    } catch (Exception ignored) {}
                });
            });

            btnMas.setOnAction(e -> {
                item.setCantidad(item.getCantidad() + 1);
                actualizarCarrito();
            });

            btnMenos.setOnAction(e -> {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - 1);
                    actualizarCarrito();
                }
            });

            btnEliminar.setOnAction(e -> {
                controller.eliminarItem(item);
                actualizarCarrito();
            });

            Label lblCant = new Label("x" + item.getCantidad());
            lblCant.setFont(Font.font(20));

            controles.getChildren().addAll(btnDescuento, btnMenos, lblCant, btnMas, btnEliminar);

            caja.getChildren().addAll(lblNombre, lblVariante, controles);

            listaItems.getChildren().add(caja);
        }

        double subtotal = controller.getTotal();
        double iva = subtotal * 0.21;
        double total = subtotal + iva;

        lblSubtotal.setText("Subtotal: " + String.format("%.2f €", subtotal));
        lblIVA.setText("IVA (21%): " + String.format("%.2f €", iva));
        lblTotal.setText("TOTAL: " + String.format("%.2f €", total));
    }
}
