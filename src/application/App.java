package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainView;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Vista principal completa (barra superior + menú lateral + lienzo central)
        MainView root = new MainView();

        // Escena principal
        Scene scene = new Scene(root, 1200, 700);

        // Cargar estilos CSS
        scene.getStylesheets().add(
            getClass().getResource("/styles/style.css").toExternalForm()
        );

        // Configuración de la ventana
        stage.setTitle("CesproTPV");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
