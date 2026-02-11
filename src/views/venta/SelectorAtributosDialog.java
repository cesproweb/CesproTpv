package views.venta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Producto;
import model.Variante;
import model.ValorAtributo;

public class SelectorAtributosDialog {

    private final Producto producto;

    public SelectorAtributosDialog(Producto producto) {
        this.producto = producto;
    }

    public java.util.Optional<Variante> showAndWait() {

        Stage ventana = new Stage();
        ventana.initModality(Modality.APPLICATION_MODAL);
        ventana.setTitle("Seleccionar variante");

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);

        ToggleGroup grupo = new ToggleGroup();

        for (Variante v : producto.getVariantes()) {
            RadioButton rb = new RadioButton(v.toString());
            rb.setToggleGroup(grupo);
            layout.getChildren().add(rb);
        }

        Button btnAceptar = new Button("Aceptar");
        btnAceptar.setOnAction(e -> ventana.close());

        layout.getChildren().add(btnAceptar);

        ventana.setScene(new javafx.scene.Scene(layout, 400, 300));
        ventana.showAndWait();

        RadioButton seleccionado = (RadioButton) grupo.getSelectedToggle();
        if (seleccionado == null) return java.util.Optional.empty();

        for (Variante v : producto.getVariantes()) {
            if (seleccionado.getText().equals(v.toString())) {
                return java.util.Optional.of(v);
            }
        }

        return java.util.Optional.empty();
    }
}
