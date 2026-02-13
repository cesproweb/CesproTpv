package views;

import controller.VentaController;
import model.Producto;
import data.DatosPrueba;

import views.venta.VentaViewA;
import views.venta.VentaViewB;
import views.venta.VentaViewC1;
import views.venta.VentaViewC2;
import views.venta.VentaViewC3;
import views.venta.VentaViewC4;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ContentDisplay;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainView extends BorderPane {

    private VentaController controller = new VentaController(DatosPrueba.cargar());

    private StackPane centerPane;
    private ImageView logoCliente;
    private Stage stage;

    public MainView(Stage stage) {
        this.stage = stage;

        setTop(createTopSection());
        setLeft(createSideMenu());
        setCenter(createCenterPane());
    }

    private VBox createTopSection() {
        VBox topSection = new VBox();
        topSection.getChildren().addAll(
            createMenuBar(),
            createRibbon(),
            createTopBar()
        );
        return topSection;
    }

    /* ============================================================
       MENÚ SUPERIOR
       ============================================================ */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("classic-menu-bar");

        Menu archivo = new Menu("Archivo");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> mostrarConfirmacionSalida());
        archivo.getItems().addAll(
            new MenuItem("Nuevo"),
            new MenuItem("Abrir"),
            new MenuItem("Guardar"),
            new MenuItem("Guardar como"),
            salir
        );

        Menu ventas = new Menu("Ventas");
        MenuItem nuevaVenta = new MenuItem("Nueva venta");
        nuevaVenta.setOnAction(e -> abrirNuevaVenta());
        ventas.getItems().addAll(
            nuevaVenta,
            new MenuItem("Selección de artículos"),
            new MenuItem("Búsqueda por código / nombre"),
            new MenuItem("Aplicar descuentos"),
            new MenuItem("Clientes (seleccionar / crear)")
        );

        Menu articulos = new Menu("Artículos");
        articulos.getItems().addAll(
            new MenuItem("Lista de artículos"),
            new MenuItem("Crear / editar / eliminar producto"),
            new MenuItem("Categorías"),
            new MenuItem("Subcategorías")
        );

        Menu clientes = new Menu("Clientes");
        clientes.getItems().addAll(
            new MenuItem("Alta de cliente"),
            new MenuItem("Edición de cliente")
        );

        Menu configuracion = new Menu("Configuración");
        MenuItem logoEmpresa = new MenuItem("Logo");
        logoEmpresa.setOnAction(e -> seleccionarLogoCliente());
        configuracion.getItems().addAll(
            new MenuItem("Datos de la empresa"),
            logoEmpresa
        );

        /* ============================================================
           SUBMENÚ SKIN
           ============================================================ */
        Menu menuSkin = new Menu("Skin");

        MenuItem skinA = new MenuItem("Andrómeda");   // Vista A
        MenuItem skinB = new MenuItem("Orión");       // Vista B
        MenuItem skinC1 = new MenuItem("Casiopea");   // Vista C1
        MenuItem skinC2 = new MenuItem("Pegaso");     // Vista C2
        MenuItem skinC3 = new MenuItem("Hydra");      // Vista C3
        MenuItem skinC4 = new MenuItem("Fénix");      // Vista C4

        skinA.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewA(controller)));
        skinB.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewB(controller)));
        skinC1.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewC1(controller)));
        skinC2.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewC2(controller)));
        skinC3.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewC3(controller)));
        skinC4.setOnAction(e -> centerPane.getChildren().setAll(new VentaViewC4(controller)));

        menuSkin.getItems().addAll(skinA, skinB, skinC1, skinC2, skinC3, skinC4);
        configuracion.getItems().add(menuSkin);

        /* ============================================================
           SUBMENÚ TEMA (CLARO / OSCURO)
           ============================================================ */
        Menu menuTema = new Menu("Tema");

        MenuItem temaClaro = new MenuItem("Claro");
        MenuItem temaOscuro = new MenuItem("Oscuro");

        temaClaro.setOnAction(e -> aplicarTema("light-theme.css"));
        temaOscuro.setOnAction(e -> aplicarTema("dark-theme.css"));

        menuTema.getItems().addAll(temaClaro, temaOscuro);
        configuracion.getItems().add(menuTema);

        Menu ayuda = new Menu("Ayuda");
        MenuItem soporte = new MenuItem("Soporte técnico");
        soporte.setOnAction(e -> mostrarVentanaSoporte());
        ayuda.getItems().addAll(
            new MenuItem("Manual de uso"),
            soporte
        );

        menuBar.getMenus().addAll(
            archivo, ventas, articulos, clientes, configuracion, ayuda
        );

        return menuBar;
    }

    /* ============================================================
       RIBBON
       ============================================================ */
    private HBox createRibbon() {
        HBox ribbon = new HBox();
        ribbon.setPadding(new Insets(8));
        ribbon.setSpacing(15);
        ribbon.getStyleClass().add("ribbon");

        Button btnVentas = createRibbonButton("Ventas", "/icons/ventas.png");
        Button btnImprimir = createRibbonButton("Imprimir", "/icons/imprimir.png");
        Button btnStock = createRibbonButton("Stock", "/icons/stock.png");
        Button btnClientes = createRibbonButton("Clientes", "/icons/clientes.png");

        ribbon.getChildren().addAll(btnVentas, btnImprimir, btnStock, btnClientes);
        return ribbon;
    }

    private Button createRibbonButton(String text, String iconPath) {
        Image img = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(25);
        iv.setFitHeight(25);

        Button button = new Button(text, iv);
        button.getStyleClass().add("ribbon-button");
        button.setContentDisplay(ContentDisplay.TOP);
        return button;
    }

    /* ============================================================
       TOP BAR
       ============================================================ */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        Label title = new Label("CesproTPV");
        title.getStyleClass().add("title");

        logoCliente = new ImageView();
        logoCliente.setFitWidth(70);
        logoCliente.setFitHeight(70);
        logoCliente.setPreserveRatio(true);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        topBar.getChildren().addAll(title, spacer, logoCliente);
        return topBar;
    }

    /* ============================================================
       MENÚ LATERAL
       ============================================================ */
    private VBox createSideMenu() {
        VBox menu = new VBox();
        menu.setPadding(new Insets(10));
        menu.setSpacing(15);
        menu.getStyleClass().add("side-menu");

        Button btnVentas = new Button("Ventas");
        Button btnProductos = new Button("Productos");
        Button btnClientes = new Button("Clientes");
        Button btnConfig = new Button("Configuración");

        btnVentas.getStyleClass().add("side-menu-button");
        btnProductos.getStyleClass().add("side-menu-button");
        btnClientes.getStyleClass().add("side-menu-button");
        btnConfig.getStyleClass().add("side-menu-button");

        btnVentas.setOnAction(e -> showScreen("VENTAS"));
        btnProductos.setOnAction(e -> showScreen("PRODUCTOS"));
        btnClientes.setOnAction(e -> showScreen("CLIENTES"));
        btnConfig.setOnAction(e -> showScreen("CONFIG"));

        menu.getChildren().addAll(btnVentas, btnProductos, btnClientes, btnConfig);
        return menu;
    }

    private StackPane createCenterPane() {
        centerPane = new StackPane();
        centerPane.getStyleClass().add("center-pane");
        return centerPane;
    }

    private void showScreen(String screen) {
        centerPane.getChildren().clear();
        Label label = new Label("Pantalla: " + screen);
        label.getStyleClass().add("screen-label");
        centerPane.getChildren().add(label);
    }

    /* ============================================================
       CARGA DE PRODUCTOS (TEMPORAL)
       ============================================================ */
    private List<Producto> cargarProductos() {
        List<Producto> lista = new ArrayList<>();

        lista.add(new Producto(
                1,
                "Incienso Natural",
                null,
                3.50,
                "/icons/incienso.png"
        ));

        return lista;
    }

    /* ============================================================
       ABRIR NUEVA VENTA
       ============================================================ */
    private void abrirNuevaVenta() {

        VentaController controller = new VentaController(DatosPrueba.cargar());
        VentaViewC4 vista = new VentaViewC4(controller);

        Scene escenaVenta = new Scene(vista, 1200, 700);
        stage.setScene(escenaVenta);
    }

    /* ============================================================
       VENTANA SOPORTE
       ============================================================ */
    private void mostrarVentanaSoporte() {
        Stage ventana = new Stage();
        ventana.setTitle("Soporte técnico");

        Image img = new Image(getClass().getResourceAsStream("/icons/cesproweb2.png"));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(80);
        iv.setFitHeight(80);

        Label texto = new Label(
            "Este programa ha sido creado por César Carrasco Cascales | Cesproweb\n\n"
          + "Versión beta.\n"
          + "Para dudas o errores:\n\n"
          + "cesproweb@gmail.com"
        );
        texto.setStyle("-fx-font-size: 15px; -fx-text-fill: #2c3e50;");
        texto.setWrapText(true);

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setStyle(
            "-fx-background-color: #3b5998; -fx-text-fill: white; "
          + "-fx-padding: 8 20; -fx-background-radius: 5;"
        );
        btnCerrar.setOnAction(e -> ventana.close());

        VBox layout = new VBox(15, iv, texto, btnCerrar);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #ecf0f1;");

        ventana.setScene(new Scene(layout, 600, 600));
        ventana.show();
    }

    /* ============================================================
       CONFIRMACIÓN DE SALIDA
       ============================================================ */
    private void mostrarConfirmacionSalida() {
        Stage ventana = new Stage();
        ventana.setTitle("Confirmar salida");

        Label texto = new Label(
            "¿Seguro que deseas salir del programa?\n\n"
          + "Se guardarán los procesos necesarios antes de cerrar."
        );
        texto.setStyle("-fx-font-size: 15px; -fx-text-fill: #2c3e50;");
        texto.setWrapText(true);

        Button btnSi = new Button("Sí, salir");
        btnSi.setStyle(
            "-fx-background-color: #c0392b; -fx-text-fill: white; "
          + "-fx-padding: 8 20; -fx-background-radius: 5;"
        );
        btnSi.setOnAction(e -> System.exit(0));

        Button btnNo = new Button("No");
        btnNo.setStyle(
            "-fx-background-color: #3b5998; -fx-text-fill: white; "
          + "-fx-padding: 8 20; -fx-background-radius: 5;"
        );
        btnNo.setOnAction(e -> ventana.close());

        HBox botones = new HBox(15, btnSi, btnNo);
        botones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, texto, botones);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(25));
        layout.setStyle("-fx-background-color: #ecf0f1;");

        ventana.setScene(new Scene(layout, 380, 200));
        ventana.show();
    }

    /* ============================================================
       SELECCIONAR LOGO CLIENTE
       ============================================================ */
    private void seleccionarLogoCliente() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar logo de la empresa");

        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        java.io.File archivo = fileChooser.showOpenDialog(getScene().getWindow());

        if (archivo != null) {
            Image img = new Image(archivo.toURI().toString());
            logoCliente.setImage(img);
        }
    }

    /* ============================================================
       APLICAR TEMA (CLARO / OSCURO)
       ============================================================ */
    private void aplicarTema(String cssFile) {
        Scene scene = getScene();
        if (scene == null) return;

        scene.getStylesheets().clear();
        scene.getStylesheets().add(
            getClass().getResource("/styles/" + cssFile).toExternalForm()
        );
    }
}
