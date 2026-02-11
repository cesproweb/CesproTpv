package views.venta;

import controller.VentaController;
import model.Producto;
import model.Variante;
import model.VentaItem;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class VentaViewB extends BorderPane {

    private VentaController controller;

    private FlowPane panelCatalogo;
    private VBox panelCarrito;

    private Label lblSubtotal;
    private Label lblIVA;
    private Label lblTotal;

    public VentaViewB(VentaController controller) {
        this.controller = controller;
        construirUI();
    }

    private void construirUI() {

        /* ============================================================
           BARRA DE BÚSQUEDA
        ============================================================ */
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar producto...");
        txtBuscar.setFont(Font.font(18));
        txtBuscar.textProperty().addListener((obs, old, nuevo) -> filtrarProductos(nuevo));

        setTop(txtBuscar);
        BorderPane.setMargin(txtBuscar, new Insets(10));

        /* ============================================================
           CATÁLOGO VISUAL
        ============================================================ */
        panelCatalogo = new FlowPane();
        panelCatalogo.setPadding(new Insets(15));
        panelCatalogo.setHgap(20);
        panelCatalogo.setVgap(20);
        panelCatalogo.setPrefWrapLength(900);

        cargarTarjetas();

        ScrollPane scrollCatalogo = new ScrollPane(panelCatalogo);
        scrollCatalogo.setFitToWidth(true);
        scrollCatalogo.setStyle("-fx-background: #f8f8f8;");

        /* ============================================================
           PANEL CARRITO
        ============================================================ */
        panelCarrito = new VBox(15);
        panelCarrito.setPadding(new Insets(15));
        panelCarrito.setPrefWidth(350);
        panelCarrito.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc;");

        construirPanelCarrito();

        /* ============================================================
           DISTRIBUCIÓN
        ============================================================ */
        SplitPane split = new SplitPane(scrollCatalogo, panelCarrito);
        split.setDividerPositions(0.70);

        setCenter(split);
    }

    /* ============================================================
       CARGAR TARJETAS DEL CATÁLOGO
    ============================================================ */
    private void cargarTarjetas() {
        panelCatalogo.getChildren().clear();

        for (Producto p : controller.getProductos()) {
            panelCatalogo.getChildren().add(new ProductoCard(p, controller, this::actualizarCarrito));
        }
    }

    /* ============================================================
       PANEL CARRITO
    ============================================================ */
    private void construirPanelCarrito() {

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
    public void actualizarCarrito() {

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

            /* ============================================================
               BOTÓN DESCUENTO (%)
            ============================================================ */
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

    /* ============================================================
       FILTRAR PRODUCTOS
    ============================================================ */
    private void filtrarProductos(String texto) {
        panelCatalogo.getChildren().clear();

        controller.getProductos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .forEach(p -> panelCatalogo.getChildren().add(new ProductoCard(p, controller, this::actualizarCarrito)));
    }
}
