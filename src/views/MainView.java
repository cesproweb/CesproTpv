package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class MainView extends BorderPane {

    private StackPane centerPane;
    private ImageView logoCliente; // Logo del cliente

    public MainView() {
        setTop(createTopSection());
        setLeft(createSideMenu());
        setCenter(createCenterPane());
    }

    /* ============================
       SECCIÓN SUPERIOR COMPLETA
       ============================ */
    private VBox createTopSection() {
        VBox topSection = new VBox();
        topSection.getChildren().addAll(
            createMenuBar(),
            createRibbon(),
            createTopBar()
        );
        return topSection;
    }

    /* ============================
       MENÚ SUPERIOR CLÁSICO
       ============================ */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("classic-menu-bar");

        // ARCHIVO
        Menu archivo = new Menu("Archivo");
        MenuItem nuevo = new MenuItem("Nuevo");
        MenuItem abrir = new MenuItem("Abrir");
        MenuItem guardar = new MenuItem("Guardar");
        MenuItem guardarComo = new MenuItem("Guardar como");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> mostrarConfirmacionSalida());
        archivo.getItems().addAll(nuevo, abrir, guardar, guardarComo, salir);

        // VENTAS
        Menu ventas = new Menu("Ventas");
        ventas.getItems().addAll(
            new MenuItem("Nueva venta"),
            new MenuItem("Selección de artículos"),
            new MenuItem("Búsqueda por código / nombre"),
            new MenuItem("Aplicar descuentos"),
            new MenuItem("Clientes (seleccionar / crear)"),
            new MenuItem("Abrir Cajón)"),
            new MenuItem("Cancelar ventas)"),
            new MenuItem("Devoluciones)"),
            new MenuItem("Reimprimir ticket)"),
            new MenuItem("Factura Simplificada)")
        );
        
        // ARTICULOS 
        Menu articulos = new Menu ("Árticulos");
        articulos.getItems().addAll(
        		new MenuItem("Lista de artículos"),
                new MenuItem("Crear / editar / eliminar producto"),
                new MenuItem("Categorías"),
                new MenuItem("Subcategorías"),
                new MenuItem("Precios"),
                new MenuItem("Impuestos"),
                new MenuItem("Códigos de Barras"),
                new MenuItem("Stock mínimo"),
                new MenuItem("Productos con variantes")
        		);
        
        // CLIENTES 
        Menu clientes = new Menu ("Clientes");
        clientes.getItems().addAll(
        		new MenuItem("Lista de artículos"),
                new MenuItem("Crear / editar / eliminar producto"),
                new MenuItem("Categorías"),
                new MenuItem("Subcategorías"),
                new MenuItem("Precios"),
                new MenuItem("Impuestos"),
                new MenuItem("Códigos de Barras"),
                new MenuItem("Stock mínimo"),
                new MenuItem("Productos con variantes")
        		);
        // EDITAR
        Menu editar = new Menu("Editar");
        editar.getItems().addAll(
            new MenuItem("Copiar"),
            new MenuItem("Pegar"),
            new MenuItem("Cortar"),
            new MenuItem("Seleccionar todo")
        );

        // VER
        Menu ver = new Menu("Ver");
        ver.getItems().addAll(
            new MenuItem("Pantalla completa"),
            new MenuItem("Zoom"),
            new MenuItem("Barra de estado")
        );

        // HERRAMIENTAS
        Menu herramientas = new Menu("Herramientas");

        MenuItem opciones = new MenuItem("Opciones");

        // SUBMENÚ PREFERENCIAS
        Menu preferencias = new Menu("Preferencias");

        MenuItem cambiarLogo = new MenuItem("Cambiar logo");
        cambiarLogo.setOnAction(e -> seleccionarLogoCliente());

        preferencias.getItems().add(cambiarLogo);

        MenuItem actualizaciones = new MenuItem("Actualizaciones");

        herramientas.getItems().addAll(opciones, preferencias, actualizaciones);

        // AYUDA
        Menu ayuda = new Menu("Ayuda");
        MenuItem soporte = new MenuItem("Soporte");
        soporte.setOnAction(e -> mostrarVentanaSoporte());
        MenuItem acerca = new MenuItem("Acerca de");
        MenuItem documentacion = new MenuItem("Documentación");
        ayuda.getItems().addAll(acerca, soporte, documentacion);

        menuBar.getMenus().addAll(
            archivo, ventas,articulos, clientes,editar, ver, herramientas, ayuda
        );

        return menuBar;
    }

    /* ============================
       RIBBON CON ICONOS
       ============================ */
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

    /* ============================
       BARRA SUPERIOR (TÍTULO + LOGO)
       ============================ */
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.getStyleClass().add("top-bar");

        Label title = new Label("CesproTPV");
        title.getStyleClass().add("title");

        logoCliente = new ImageView();
        logoCliente.setFitWidth(45);
        logoCliente.setFitHeight(45);
        logoCliente.setPreserveRatio(true);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        topBar.getChildren().addAll(title, spacer, logoCliente);
        return topBar;
    }

    /* ============================
       MENÚ LATERAL
       ============================ */
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

    /* ============================
       ZONA CENTRAL
       ============================ */
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

    /* ============================
       VENTANA SOPORTE
       ============================ */
    private void mostrarVentanaSoporte() {
        javafx.stage.Stage ventana = new javafx.stage.Stage();
        ventana.setTitle("Soporte técnico");

        Image img = new Image(getClass().getResourceAsStream("/icons/cesproweb2.png"));
        ImageView iv = new ImageView(img);
        iv.setFitWidth(80);
        iv.setFitHeight(80);

        Label texto = new Label(
            "Este programa ha sido creado por César Carrasco Cascales | Cesproweb\n\n"
          + "Esta es la versión beta de la aplicación.\n"
          + "Cualquier duda o error que encuentre no dude en notificármelo.\n\n"
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

        ventana.setScene(new javafx.scene.Scene(layout, 600, 600));
        ventana.show();
    }

    /* ============================
       CONFIRMACIÓN DE SALIDA
       ============================ */
    private void mostrarConfirmacionSalida() {
        javafx.stage.Stage ventana = new javafx.stage.Stage();
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

        ventana.setScene(new javafx.scene.Scene(layout, 380, 200));
        ventana.show();
    }

    /* ============================
       SELECCIONAR LOGO DEL CLIENTE
       ============================ */
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
}
