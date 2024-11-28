package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.controller;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;

public class MainApp extends Application {

    private VBox mainLayout;

    @Override
    public void start(Stage primaryStage) {
        // Crear la barra de menú con mnemonics
        MenuBar menuBar = createMenuBar(primaryStage);

        // Crear el panel principal de la interfaz
        mainLayout = new VBox();
        mainLayout.setPadding(new Insets(15));
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(mainLayout);  // Área donde se mostrarán los formularios

        // Crear la escena y asignarla a la ventana
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema de Pedidos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para crear la barra de menú con mnemonics
    private MenuBar createMenuBar(Stage primaryStage) {
        // Menú Sistema
        Menu menuSistema = new Menu("_Sistema");
        MenuItem acercaDeMenuItem = new MenuItem("_Acerca de");
        MenuItem salirMenuItem = new MenuItem("_Salir");
        menuSistema.getItems().addAll(acercaDeMenuItem, salirMenuItem);

        // Menú Mantenimiento
        Menu menuMantenimiento = new Menu("_Mantenimiento");

        // Submenú Insertar
        Menu insertMenu = new Menu("_Insertar");
        MenuItem insertarClienteMenuItem = new MenuItem("_Insertar Cliente");
        insertarClienteMenuItem.setOnAction(e -> openInsertarCliente());
        MenuItem insertarArticuloMenuItem = new MenuItem("_Insertar Artículo");
        insertarArticuloMenuItem.setOnAction(e -> openInsertarProducto());
        insertMenu.getItems().addAll(insertarClienteMenuItem, insertarArticuloMenuItem);

        // Submenú Borrar
        Menu deleteMenu = new Menu("_Borrar");
        MenuItem borrarClienteMenuItem = new MenuItem("_Borrar Cliente");
        borrarClienteMenuItem.setOnAction(e -> openBorrarCliente());
        MenuItem borrarArticuloMenuItem = new MenuItem("_Borrar Artículo");
        borrarArticuloMenuItem.setOnAction(e -> openBorrarProducto());
        deleteMenu.getItems().addAll(borrarClienteMenuItem, borrarArticuloMenuItem);

        // Agregar los submenús al menú de Mantenimiento
        menuMantenimiento.getItems().addAll(insertMenu, deleteMenu);

        // Menú Transacciones
        Menu menuTransacciones = new Menu("_Transacciones");
        MenuItem nuevoPedidoMenuItem = new MenuItem("_Nuevo Pedido");
        MenuItem cierreDiaMenuItem = new MenuItem("_Cierre del Día");
        menuTransacciones.getItems().addAll(nuevoPedidoMenuItem, cierreDiaMenuItem);

        // Menú Reportes
        Menu menuReportes = new Menu("_Reportes");
        MenuItem listadoClientesMenuItem = new MenuItem("_Listado de Clientes");
        MenuItem listadoArticulosMenuItem = new MenuItem("_Listado de Artículos");
        MenuItem graficoVentasMenuItem = new MenuItem("_Gráfico de Ventas Semanales");
        menuReportes.getItems().addAll(listadoClientesMenuItem, listadoArticulosMenuItem, graficoVentasMenuItem);

        // Crear la barra de menú y agregar los menús
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menuSistema, menuMantenimiento, menuTransacciones, menuReportes);

        return menuBar;
    }

    // Método para abrir la vista de Insertar Cliente en el menú principal
    private void openInsertarCliente() {
        // Limpiar el contenido actual
        mainLayout.getChildren().clear();

        // Crear una instancia del controlador ClientController
        ClientController clientController = new ClientController();
        VBox clientLayout = clientController.getLayout();  // Llamamos al método getLayout()

        // Añadir el formulario de inserción de cliente al layout principal
        mainLayout.getChildren().add(clientLayout);
    }

    // Método para abrir la vista de Insertar Producto en el menú principal
    private void openInsertarProducto() {
        // Limpiar el contenido actual
        mainLayout.getChildren().clear();

        // Crear una instancia del controlador ProductController
        ProductController productController = new ProductController();
        VBox productLayout = productController.getLayout();  // Llamamos al método getLayout() para inserción

        // Añadir el formulario de inserción de producto al layout principal
        mainLayout.getChildren().add(productLayout);
    }


    // Método para abrir la vista de Borrar Cliente
    private void openBorrarCliente() {
        mainLayout.getChildren().clear();
        ClientController clientController = new ClientController();
        VBox clientLayout = clientController.getLayoutParaEliminar(); // Esta nueva vista contiene la lista de clientes
        mainLayout.getChildren().add(clientLayout);
    }

    // Método para abrir la vista de Borrar Producto
    private void openBorrarProducto() {
        mainLayout.getChildren().clear();
        ProductController productController = new ProductController();
        VBox productLayout = productController.getLayoutParaEliminar(); // Vista para eliminar productos
        mainLayout.getChildren().add(productLayout);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
