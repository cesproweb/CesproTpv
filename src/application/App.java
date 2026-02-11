package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.MainView;

public class App extends Application {

    @Override
    public void start(Stage stage) {

        // Cargar la vista principal y pasarle el Stage
        MainView main = new MainView(stage);

        // Crear escena
        Scene scene = new Scene(main, 1200, 700);

        // 🔵 Cargar tu CSS azul original
        scene.getStylesheets().add(
            getClass().getResource("/styles/style.css").toExternalForm()
        );

        // Configurar ventana
        stage.setTitle("CesproTPV");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
