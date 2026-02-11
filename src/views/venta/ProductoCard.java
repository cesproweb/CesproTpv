package views.venta;

import controller.VentaController;
import model.Producto;
import model.Variante;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class ProductoCard extends VBox {

    private final Producto producto;
    private final VentaController controller;
    private final Runnable callbackActualizarCarrito;

    public ProductoCard(Producto producto, VentaController controller, Runnable callbackActualizarCarrito) {
        this.producto = producto;
        this.controller = controller;
        this.callbackActualizarCarrito = callbackActualizarCarrito;
        construirUI();
    }

    private void construirUI() {

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.TOP_CENTER);
        setPrefSize(180, 220);

        setStyle("""
                -fx-background-color: white;
                -fx-border-color: #ccc;
                -fx-border-radius: 10;
                -fx-background-radius: 10;
                -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8, 0, 0, 2);
                """);

        ImageView img = new ImageView();
        img.setFitWidth(120);
        img.setFitHeight(120);
        img.setPreserveRatio(true);

        Image imagen;

        try {
            imagen = new Image(getClass().getResourceAsStream(producto.getImagen()));
        } catch (Exception e) {
            imagen = new Image(getClass().getResourceAsStream("/icons/default.png"));
        }

        img.setImage(imagen);

        Label lblNombre = new Label(producto.getNombre());
        lblNombre.setFont(Font.font(16));
        lblNombre.setWrapText(true);
        lblNombre.setAlignment(Pos.CENTER);

        Label lblPrecio = new Label(String.format("%.2f €", producto.getPrecioBase()));
        lblPrecio.setFont(Font.font(18));
        lblPrecio.setStyle("-fx-text-fill: #333;");

        Button btnAdd = new Button("Añadir");
        btnAdd.setFont(Font.font(16));
        btnAdd.setMaxWidth(Double.MAX_VALUE);
        btnAdd.setStyle("""
                -fx-background-color: #4CAF50;
                -fx-text-fill: white;
                -fx-background-radius: 8;
                -fx-padding: 8 12;
                """);

        btnAdd.setOnAction(e -> añadirProducto());

        getChildren().addAll(img, lblNombre, lblPrecio, btnAdd);
    }

    private void añadirProducto() {

        Variante varianteSeleccionada = null;

        if (!producto.getVariantes().isEmpty()) {
            SelectorAtributosDialog dialog = new SelectorAtributosDialog(producto);
            varianteSeleccionada = dialog.showAndWait().orElse(null);

            if (varianteSeleccionada == null) {
                return;
            }
        }

        controller.addProducto(producto, varianteSeleccionada);

        callbackActualizarCarrito.run();
    }
}
