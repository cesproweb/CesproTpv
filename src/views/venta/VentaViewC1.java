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

public class VentaViewC1 extends BorderPane {

    private final VentaController controller;

    private VBox panelCategorias;
    private FlowPane panelProductos;
    private VBox panelCarrito;

    private Label lblSubtotal;
    private Label lblIVA;
    private Label lblTotal;

    public VentaViewC1(VentaController controller) {
        this.controller = controller;
        construirUI();
    }

    private void construirUI() {

        /* ============================================================
           PANEL IZQUIERDO — CATEGORÍAS
        ============================================================ */
        panelCategorias = new VBox(10);
        panelCategorias.setPadding(new Insets(10));
        panelCategorias.setPrefWidth(180);
        panelCategorias.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc;");

        Label lblCat = new Label("Categorías");
        lblCat.setFont(Font.font(20));

        panelCategorias.getChildren().add(lblCat);

        for (Categoria cat : Categoria.values()) {
            Button btn = new Button(cat.name());
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-font-size: 16px;");

            btn.setOnAction(e -> filtrarPorCategoria(cat));

            panelCategorias.getChildren().add(btn);
        }

        setLeft(panelCategorias);

        /* ============================================================
           PANEL CENTRAL — PRODUCTOS (tarjetas)
        ============================================================ */
        panelProductos = new FlowPane();
        panelProductos.setPadding(new Insets(15));
        panelProductos.setHgap(20);
        panelProductos.setVgap(20);
        panelProductos.setPrefWrapLength(900);

        cargarTarjetas(controller.getProductos());

        ScrollPane scrollProductos = new ScrollPane(panelProductos);
        scrollProductos.setFitToWidth(true);

        setCenter(scrollProductos);

        /* ============================================================
           PANEL DERECHO — CARRITO
        ============================================================ */
        panelCarrito = new VBox(15);
        panelCarrito.setPadding(new Insets(15));
        panelCarrito.setPrefWidth(500);
        panelCarrito.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc;");

        construirCarrito();

        setRight(panelCarrito);
    }

    /* ============================================================
       CARGAR TARJETAS DE PRODUCTOS
    ============================================================ */
    private void cargarTarjetas(java.util.List<Producto> productos) {
        panelProductos.getChildren().clear();

        for (Producto p : productos) {
            panelProductos.getChildren().add(
                    new ProductoCard(p, controller, this::actualizarCarrito)
            );
        }
    }

    /* ============================================================
       FILTRAR POR CATEGORÍA
    ============================================================ */
    private void filtrarPorCategoria(Categoria categoria) {
        var filtrados = controller.getProductos().stream()
                .filter(p -> p.getCategoria() == categoria)
                .toList();

        cargarTarjetas(filtrados);
    }

    /* ============================================================
       PANEL CARRITO
    ============================================================ */
    private void construirCarrito() {

        Label titulo = new Label("Carrito");
        titulo.setFont(Font.font(22));

        VBox listaItems = new VBox(10);
        listaItems.setPrefHeight(500);

        ScrollPane scrollItems = new ScrollPane(listaItems);
        scrollItems.setFitToWidth(true);

        lblSubtotal = new Label();
        lblIVA = new Label();
        lblTotal = new Label();

        lblSubtotal.setFont(Font.font(16));
        lblIVA.setFont(Font.font(16));
        lblTotal.setFont(Font.font(20));

        Button btnCobrar = new Button("Cobrar");
        btnCobrar.setMaxWidth(Double.MAX_VALUE);
        btnCobrar.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white;");

        Button btnVaciar = new Button("Vaciar");
        btnVaciar.setMaxWidth(Double.MAX_VALUE);
        btnVaciar.setStyle("-fx-font-size: 16px;");
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

            HBox fila = new HBox(10);
            fila.setAlignment(Pos.CENTER_LEFT);
            fila.setPadding(new Insets(5));
            fila.setStyle("-fx-background-color: white; -fx-border-color: #ddd;");

            Label lblNombre = new Label(item.getProducto().getNombre());
            lblNombre.setFont(Font.font(16));

            Label lblVariante = new Label(
                    item.getVariante() != null ? item.getVariante().toString() : ""
            );
            lblVariante.setStyle("-fx-text-fill: #666;");

            Button btnMas = new Button("+");
            Button btnMenos = new Button("-");
            Button btnEliminar = new Button("X");

            Button btnDescuento = new Button("%");
            btnDescuento.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");

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
            lblCant.setFont(Font.font(16));

            Region espacio = new Region();
            HBox.setHgrow(espacio, Priority.ALWAYS);

            fila.getChildren().addAll(
                    lblNombre,
                    lblVariante,
                    espacio,
                    btnDescuento,
                    btnMenos,
                    lblCant,
                    btnMas,
                    btnEliminar
            );

            listaItems.getChildren().add(fila);
        }

        double subtotal = controller.getTotal();
        double iva = subtotal * 0.21;
        double total = subtotal + iva;

        lblSubtotal.setText("Subtotal: " + String.format("%.2f €", subtotal));
        lblIVA.setText("IVA (21%): " + String.format("%.2f €", iva));
        lblTotal.setText("TOTAL: " + String.format("%.2f €", total));
    }
}

