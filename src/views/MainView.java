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
    private ImageView logoCliente;

    public MainView() {
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
       MENÚ SUPERIOR COMPLETO (TPV PROFESIONAL)
       ============================================================ */
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("classic-menu-bar");

        /* ============================
           ARCHIVO
           ============================ */
        Menu archivo = new Menu("Archivo");
        MenuItem nuevo = new MenuItem("Nuevo");
        MenuItem abrir = new MenuItem("Abrir");
        MenuItem guardar = new MenuItem("Guardar");
        MenuItem guardarComo = new MenuItem("Guardar como");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(e -> mostrarConfirmacionSalida());
        archivo.getItems().addAll(nuevo, abrir, guardar, guardarComo, salir);

        /* ============================
           VENTAS / TPV
           ============================ */
        Menu ventas = new Menu("Ventas");

        MenuItem nuevaVenta = new MenuItem("Nueva venta");
        MenuItem seleccionArticulos = new MenuItem("Selección de artículos");
        MenuItem busqueda = new MenuItem("Búsqueda por código / nombre");
        MenuItem descuentos = new MenuItem("Aplicar descuentos");
        MenuItem clientesVenta = new MenuItem("Clientes (seleccionar / crear)");

        // Submenú Métodos de pago
        Menu metodosPago = new Menu("Métodos de pago");
        metodosPago.getItems().addAll(
            new MenuItem("Efectivo"),
            new MenuItem("Tarjeta"),
            new MenuItem("Transferencia"),
            new MenuItem("Bizum"),
            new MenuItem("Pago mixto")
        );

        MenuItem abrirCajon = new MenuItem("Abrir cajón");
        MenuItem cancelarVenta = new MenuItem("Cancelar / pausar venta");
        MenuItem devoluciones = new MenuItem("Devoluciones");
        MenuItem reimpresion = new MenuItem("Reimpresión de ticket");
        MenuItem factura = new MenuItem("Factura simplificada / completa");

        ventas.getItems().addAll(
            nuevaVenta, seleccionArticulos, busqueda, descuentos,
            clientesVenta, metodosPago, abrirCajon, cancelarVenta,
            devoluciones, reimpresion, factura
        );

        /* ============================
           ARTÍCULOS / PRODUCTOS
           ============================ */
        Menu articulos = new Menu("Artículos");
        articulos.getItems().addAll(
            new MenuItem("Lista de artículos"),
            new MenuItem("Crear / editar / eliminar producto"),
            new MenuItem("Categorías"),
            new MenuItem("Subcategorías"),
            new MenuItem("Precios"),
            new MenuItem("Impuestos"),
            new MenuItem("Códigos de barras"),
            new MenuItem("Stock mínimo"),
            new MenuItem("Productos con variantes")
        );

        /* ============================
           CLIENTES
           ============================ */
        Menu clientes = new Menu("Clientes");
        clientes.getItems().addAll(
            new MenuItem("Alta de cliente"),
            new MenuItem("Edición de cliente"),
            new MenuItem("Historial de compras"),
            new MenuItem("Saldo / crédito"),
            new MenuItem("Descuentos personalizados"),
            new MenuItem("Facturación a cliente"),
            new MenuItem("Fidelización (puntos, tarjetas)")
        );

        /* ============================
           PROVEEDORES
           ============================ */
        Menu proveedores = new Menu("Proveedores");
        proveedores.getItems().addAll(
            new MenuItem("Alta de proveedor"),
            new MenuItem("Pedidos a proveedor"),
            new MenuItem("Recepción de mercancía"),
            new MenuItem("Albaranes"),
            new MenuItem("Facturas de proveedor"),
            new MenuItem("Coste de producto")
        );

        /* ============================
           ALMACÉN / STOCK
           ============================ */
        Menu stock = new Menu("Stock");
        stock.getItems().addAll(
            new MenuItem("Entradas de stock"),
            new MenuItem("Salidas de stock"),
            new MenuItem("Regularización"),
            new MenuItem("Inventario"),
            new MenuItem("Stock por tienda / almacén"),
            new MenuItem("Traspasos entre tiendas"),
            new MenuItem("Avisos de stock bajo")
        );

        /* ============================
           CAJA
           ============================ */
        Menu caja = new Menu("Caja");
        caja.getItems().addAll(
            new MenuItem("Apertura de caja"),
            new MenuItem("Cierre de caja"),
            new MenuItem("Arqueo"),
            new MenuItem("Entradas de efectivo"),
            new MenuItem("Salidas de efectivo"),
            new MenuItem("Movimientos de caja"),
            new MenuItem("Diferencias de caja"),
            new MenuItem("Informes diarios")
        );

        /* ============================
           INFORMES
           ============================ */
        Menu informes = new Menu("Informes");
        informes.getItems().addAll(
            new MenuItem("Ventas por día / mes / año"),
            new MenuItem("Ventas por empleado"),
            new MenuItem("Ventas por producto"),
            new MenuItem("Ventas por categoría"),
            new MenuItem("Beneficio / margen"),
            new MenuItem("Impuestos (IVA)"),
            new MenuItem("Métodos de pago"),
            new MenuItem("Top ventas"),
            new MenuItem("Productos sin rotación")
        );

        /* ============================
           USUARIOS / EMPLEADOS
           ============================ */
        Menu usuarios = new Menu("Usuarios");
        usuarios.getItems().addAll(
            new MenuItem("Alta de usuario"),
            new MenuItem("Roles"),
            new MenuItem("Permisos"),
            new MenuItem("Turnos"),
            new MenuItem("Registro de actividad"),
            new MenuItem("Ventas por empleado")
        );

        /* ============================
        CONFIGURACIÓN
        ============================ */
     Menu configuracion = new Menu("Configuración");

     MenuItem datosEmpresa = new MenuItem("Datos de la empresa");
     MenuItem impuestos = new MenuItem("Impuestos");
     MenuItem seriesFacturacion = new MenuItem("Series de facturación");
     MenuItem formatoTicket = new MenuItem("Formato de ticket");

     // ✔ Aquí está la opción de cambiar logo funcionando
     MenuItem logoEmpresa = new MenuItem("Logo");
     logoEmpresa.setOnAction(e -> seleccionarLogoCliente());

     MenuItem idioma = new MenuItem("Idioma");
     MenuItem moneda = new MenuItem("Moneda");
     MenuItem metodosPagoConfig = new MenuItem("Métodos de pago");
     MenuItem hardware = new MenuItem("Integración con hardware");
     MenuItem backups = new MenuItem("Copias de seguridad");

     configuracion.getItems().addAll(
         datosEmpresa,
         impuestos,
         seriesFacturacion,
         formatoTicket,
         logoEmpresa,   // ← AQUÍ ESTÁ TU OPCIÓN FUNCIONAL
         idioma,
         moneda,
         metodosPagoConfig,
         hardware,
         backups
     );
;

        /* ============================
           AYUDA / SISTEMA
           ============================ */
        Menu ayuda = new Menu("Ayuda");
        MenuItem soporte = new MenuItem("Soporte técnico");
        soporte.setOnAction(e -> mostrarVentanaSoporte());
        ayuda.getItems().addAll(
            new MenuItem("Manual de uso"),
            soporte,
            new MenuItem("Actualizaciones"),
            new MenuItem("Licencia"),
            new MenuItem("Cerrar sesión"),
            new MenuItem("Salir del programa")
        );

        /* ============================
           AÑADIR TODO AL MENÚ SUPERIOR
           ============================ */
        menuBar.getMenus().addAll(
            archivo, ventas, articulos, clientes, proveedores,
            stock, caja, informes, usuarios, configuracion, ayuda
        );

        return menuBar;
    }

    /* ============================================================
       RIBBON (NO MODIFICADO)
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
       BARRA SUPERIOR (TÍTULO + LOGO)
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
        logoCliente.setFitWidth(70);   // ← MÁS GRANDE
        logoCliente.setFitHeight(70);  // ← MÁS GRANDE
        logoCliente.setPreserveRatio(true);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        topBar.getChildren().addAll(title, spacer, logoCliente);
        return topBar;
    }

    /* ============================================================
       MENÚ LATERAL (NO MODIFICADO)
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
       VENTANA SOPORTE
       ============================================================ */
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

    /* ============================================================
       CONFIRMACIÓN DE SALIDA
       ============================================================ */
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

    /* ============================================================
       SELECCIONAR LOGO DEL CLIENTE
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
}
