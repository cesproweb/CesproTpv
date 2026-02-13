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

public class VentaViewC3 extends BorderPane {

    private final VentaController controller;

    private TableView<Producto> tablaProductos;
    private FlowPane panelTarjetas;
    private VBox panelCarrito;

    private Label lblSubtotal;
    private Label lblIVA;
    private Label lblTotal;

    public VentaViewC3(VentaController controller) {
        this.controller = controller;
        construirUI();
    }

    private void construirUI() {

        /* ============================================================
           LISTA DE PRODUCTOS A LA IZQUIERDA
        ============================================================ */
        tablaProductos = new TableView<>();
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tablaProductos.setPrefWidth(300);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Producto");
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));

        tablaProductos.getColumns().add(colNombre);
        tablaProductos.getItems().addAll(controller.getProductos());

        tablaProductos.setOnMouseClicked(e -> {
            if (e.getClickCount() == 1) {
                Producto p = tablaProductos.getSelectionModel().getSelectedItem();
                if (p != null) {
                    cargarTarjetas(java.util.List.of(p));
                }
            }
        });

        setLeft(tablaProductos);

        /* ============================================================
           TARJETAS A LA DERECHA
        ============================================================ */
        panelTarjetas = new FlowPane();
        panelTarjetas.setPadding(new Insets(15));
        panelTarjetas.setHgap(20);
        panelTarjetas.setVgap(20);
        panelTarjetas.setPrefWrapLength(900);

        cargarTarjetas(controller.getProductos());

        ScrollPane scrollTarjetas = new ScrollPane(panelTarjetas);
        scrollTarjetas.setFitToWidth(true);

        setCenter(scrollTarjetas);

        /* ============================================================
           CARRITO ABAJO
        ============================================================ */
        panelCarrito = new VBox(10);
        panelCarrito.setPadding(new Insets(10));
        panelCarrito.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc;");

        construirCarrito();

        setBottom(panelCarrito);
    }

    /* ============================================================
       CARGAR TARJETAS
    ============================================================ */
    private void cargarTarjetas(java.util.List<Producto> productos) {
        panelTarjetas.getChildren().clear();

        for (Producto p : productos) {
            panelTarjetas.getChildren().add(
                    new ProductoCard(p, controller, this::actualizarCarrito)
            );
        }
    }

    /* ============================================================
       PANEL CARRITO
    ============================================================ */
    private void construirCarrito() {

        Label titulo = new Label("Carrito");
        titulo.setFont(Font.font(20));

        VBox listaItems = new VBox(10);
        listaItems.setPadding(new Insets(5));

        ScrollPane scrollItems = new ScrollPane(listaItems);
        scrollItems.setFitToWidth(true);
        scrollItems.setPrefHeight(200);

        lblSubtotal = new Label();
        lblIVA = new Label();
        lblTotal = new Label();

        lblSubtotal.setFont(Font.font(16));
        lblIVA.setFont(Font.font(16));
        lblTotal.setFont(Font.font(18));

        Button btnCobrar = new Button("Cobrar");
        btnCobrar.setStyle("-fx-font-size: 18px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnCobrar.setMaxWidth(Double.MAX_VALUE);

        Button btnVaciar = new Button("Vaciar");
        btnVaciar.setMaxWidth(Double.MAX_VALUE);
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
