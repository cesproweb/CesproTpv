package views.venta;

import controller.VentaController;
import model.Producto;
import model.Variante;
import model.VentaItem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class VentaViewA extends BorderPane {

    private VentaController controller;

    private TableView<Producto> tablaProductos;
    private TableView<VentaItem> tablaCarrito;

    private Label lblSubtotal;
    private Label lblIVA;
    private Label lblTotal;

    public VentaViewA(VentaController controller) {
        this.controller = controller;
        construirUI();
    }

    private void construirUI() {

        /* ============================================================
           BARRA DE BÚSQUEDA
        ============================================================ */
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar producto...");
        txtBuscar.textProperty().addListener((obs, old, nuevo) -> filtrarProductos(nuevo));

        setTop(txtBuscar);
        BorderPane.setMargin(txtBuscar, new Insets(10));

        /* ============================================================
           TABLA DE PRODUCTOS
        ============================================================ */
        tablaProductos = new TableView<>();
        tablaProductos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Producto");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getCategoria() != null
                                ? c.getValue().getCategoria().name()
                                : ""
                )
        );

        tablaProductos.getColumns().addAll(colNombre, colCategoria);

        tablaProductos.getItems().addAll(controller.getProductos());

        // Colores por categoría
        tablaProductos.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);
                if (p == null || empty || p.getCategoria() == null) {
                    setStyle("");
                    return;
                }

                switch (p.getCategoria()) {
                    case INCIENSO -> setStyle("-fx-background-color: #d0e8ff;");
                    case FIGURA -> setStyle("-fx-background-color: #d8ffd0;");
                    case LAMINA -> setStyle("-fx-background-color: #ffe6cc;");
                    case IMAN -> setStyle("-fx-background-color: #f2ccff;");
                    case PACK -> setStyle("-fx-background-color: #ffd6d6;");
                    case INCENSARIO -> setStyle("-fx-background-color: #fff2cc;");
                    case JUEGO -> setStyle("-fx-background-color: #e6e6ff;");
                    default -> setStyle("");
                }
            }
        });

        // Doble clic para añadir al carrito
        tablaProductos.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Producto p = tablaProductos.getSelectionModel().getSelectedItem();
                if (p != null) {
                    Variante v = null;

                    if (!p.getVariantes().isEmpty()) {
                        ChoiceDialog<Variante> dialog = new ChoiceDialog<>(p.getVariantes().get(0), p.getVariantes());
                        dialog.setTitle("Seleccionar variante");
                        dialog.setHeaderText("Elige tamaño/peso/color");
                        v = dialog.showAndWait().orElse(null);
                    }

                    controller.addProducto(p, v);
                    tablaCarrito.getItems().setAll(controller.getCarrito());
                    actualizarTotales();
                }
            }
        });

        /* ============================================================
           TABLA DEL CARRITO
        ============================================================ */
        tablaCarrito = new TableView<>();
        tablaCarrito.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<VentaItem, String> colCarritoNombre = new TableColumn<>("Producto");
        colCarritoNombre.setCellValueFactory(c ->
                new SimpleStringProperty(c.getValue().getProducto().getNombre()));

        TableColumn<VentaItem, String> colCarritoVariante = new TableColumn<>("Variante");
        colCarritoVariante.setCellValueFactory(c ->
                new SimpleStringProperty(
                        c.getValue().getVariante() != null ? c.getValue().getVariante().toString() : "-"
                ));

        TableColumn<VentaItem, Integer> colCarritoCantidad = new TableColumn<>("Cant.");
        colCarritoCantidad.setCellValueFactory(c ->
                new SimpleIntegerProperty(c.getValue().getCantidad()).asObject());

        colCarritoCantidad.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colCarritoCantidad.setOnEditCommit(e -> {
            VentaItem item = e.getRowValue();
            item.setCantidad(e.getNewValue());
            tablaCarrito.refresh();
            actualizarTotales();
        });

        TableColumn<VentaItem, Double> colDescuento = new TableColumn<>("Desc. %");
        colDescuento.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getDescuentoPorcentaje()).asObject());

        colDescuento.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colDescuento.setOnEditCommit(e -> {
            VentaItem item = e.getRowValue();
            item.setDescuentoPorcentaje(e.getNewValue());
            tablaCarrito.refresh();
            actualizarTotales();
        });

        TableColumn<VentaItem, Double> colCarritoSubtotal = new TableColumn<>("Subtotal");
        colCarritoSubtotal.setCellValueFactory(c ->
                new SimpleDoubleProperty(c.getValue().getSubtotal()).asObject());

        tablaCarrito.getColumns().addAll(
                colCarritoNombre, colCarritoVariante, colCarritoCantidad, colDescuento, colCarritoSubtotal
        );

        tablaCarrito.setEditable(true);

        /* ============================================================
           BOTONES DEL CARRITO
        ============================================================ */
        Button btnMas = new Button("+");
        Button btnMenos = new Button("-");
        Button btnEliminar = new Button("Eliminar");

        btnMas.setOnAction(e -> {
            VentaItem item = tablaCarrito.getSelectionModel().getSelectedItem();
            if (item != null) {
                item.setCantidad(item.getCantidad() + 1);
                tablaCarrito.refresh();
                actualizarTotales();
            }
        });

        btnMenos.setOnAction(e -> {
            VentaItem item = tablaCarrito.getSelectionModel().getSelectedItem();
            if (item != null && item.getCantidad() > 1) {
                item.setCantidad(item.getCantidad() - 1);
                tablaCarrito.refresh();
                actualizarTotales();
            }
        });

        btnEliminar.setOnAction(e -> {
            VentaItem item = tablaCarrito.getSelectionModel().getSelectedItem();
            if (item != null) {
                controller.eliminarItem(item);
                tablaCarrito.getItems().setAll(controller.getCarrito());
                actualizarTotales();
            }
        });

        tablaCarrito.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                VentaItem item = tablaCarrito.getSelectionModel().getSelectedItem();
                if (item != null) {
                    controller.eliminarItem(item);
                    tablaCarrito.getItems().setAll(controller.getCarrito());
                    actualizarTotales();
                }
            }
        });

        HBox accionesCarrito = new HBox(10, btnMas, btnMenos, btnEliminar);
        accionesCarrito.setPadding(new Insets(10));

        VBox carritoBox = new VBox(10, tablaCarrito, accionesCarrito);

        /* ============================================================
           PANEL DE TOTALES
        ============================================================ */
        VBox panelCobro = new VBox(10);
        panelCobro.setPadding(new Insets(10));
        panelCobro.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc;");
        panelCobro.setPrefWidth(250);

        lblSubtotal = new Label();
        lblIVA = new Label();
        lblTotal = new Label();

        actualizarTotales();

        Button btnCobrar = new Button("Cobrar");
        btnCobrar.setMaxWidth(Double.MAX_VALUE);

        Button btnVaciar = new Button("Vaciar");
        btnVaciar.setMaxWidth(Double.MAX_VALUE);

        btnVaciar.setOnAction(e -> {
            controller.limpiarCarrito();
            tablaCarrito.getItems().clear();
            actualizarTotales();
        });

        panelCobro.getChildren().addAll(lblSubtotal, lblIVA, lblTotal, btnCobrar, btnVaciar);

        /* ============================================================
           DISTRIBUCIÓN
        ============================================================ */
        SplitPane centro = new SplitPane(tablaProductos, carritoBox);
        centro.setDividerPositions(0.5);

        setCenter(centro);
        setRight(panelCobro);
    }

    /* ============================================================
       ACTUALIZAR TOTALES
    ============================================================ */
    private void actualizarTotales() {
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
        var lista = controller.getProductos().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase()))
                .toList();

        tablaProductos.getItems().setAll(lista);
    }
}
