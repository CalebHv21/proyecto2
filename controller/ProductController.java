package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.controller;

import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.data.ProductoData;
import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.io.IOException;

public class ProductController {

    private VBox layout;
    private ObservableList<Product> productosObservableList;

    public ProductController() {
        createUI();
    }

    // Método para crear la UI de inserción de producto
    public void createUI() {
        layout = new VBox(10);
        layout.setPadding(new Insets(15));

        // Titulo
        Label lblTitulo = new Label("Insertar un nuevo producto");

        // Campos para el formulario
        Label lblDescripcion = new Label("Descripción");
        TextField tfDescripcion = new TextField();
        Label lblPrecio = new Label("Precio");
        TextField tfPrecio = new TextField();
        Label lblCantidad = new Label("Cantidad");
        TextField tfCantidad = new TextField();

        // Crear el GridPane
        GridPane pane = new GridPane();
        pane.add(lblDescripcion, 0, 0);
        pane.add(tfDescripcion, 1, 0);
        pane.add(lblPrecio, 0, 1);
        pane.add(tfPrecio, 1, 1);
        pane.add(lblCantidad, 0, 2);
        pane.add(tfCantidad, 1, 2);

        // Botones
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> handleBtnInsertar(tfDescripcion.getText(), tfPrecio.getText(), tfCantidad.getText()));
        Button btnCancelar = new Button("Cancelar");
        pane.add(btnGuardar, 0, 3);
        pane.add(btnCancelar, 1, 3);

        this.layout.getChildren().addAll(lblTitulo, pane);
    }

    // Método para manejar la inserción de un producto
    public void handleBtnInsertar(String descripcion, String precio, String cantidad) {
        try {
            ProductoData productoData = new ProductoData();
            Product newProduct = new Product(null, descripcion, Double.parseDouble(precio), Integer.parseInt(cantidad));  // El ID será generado automáticamente
            productoData.insertar(newProduct);  // Inserta el producto en el archivo

            // Mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El producto ha sido insertado correctamente.");
            alert.showAndWait();
        } catch (IOException e) {
            // Manejo de errores
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un error al guardar el producto.");
            alert.showAndWait();
        }
    }

    // Método para obtener el layout de inserción
    public VBox getLayout() {
        return layout;
    }

    // Método para mostrar la lista de productos y permitir su eliminación
    public VBox getLayoutParaEliminar() {
        layout = new VBox(10);
        layout.setPadding(new Insets(15));

        // Crear la tabla de productos
        TableView<Product> table = new TableView<>();
        TableColumn<Product, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());  // Aquí ya no usamos asString()
        TableColumn<Product, String> colDescripcion = new TableColumn<>("Descripción");
        colDescripcion.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        TableColumn<Product, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asString());  // Correcto usar asString()
        TableColumn<Product, String> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asString());  // Correcto usar asString()

        table.getColumns().addAll(colId, colDescripcion, colPrecio, colCantidad);

        // Cargar la lista de productos
        try {
            ProductoData productoData = new ProductoData();
            productosObservableList = FXCollections.observableArrayList();
            for (String productoString : productoData.findAll()) {
                String[] datos = productoString.split(";");
                Product producto = new Product(datos[0], datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]));
                productosObservableList.add(producto);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setItems(productosObservableList);

        // Botón eliminar producto
        Button btnEliminar = new Button("Eliminar Producto");
        btnEliminar.setOnAction(e -> handleEliminarProducto(table));

        layout.getChildren().addAll(table, btnEliminar);

        return layout;
    }

    // Método para manejar la eliminación de un producto
    private void handleEliminarProducto(TableView<Product> table) {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                ProductoData productoData = new ProductoData();
                // Eliminar el producto del archivo
                productoData.eliminarProducto(selectedProduct);
                // Actualizar la lista
                productosObservableList.remove(selectedProduct);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El producto ha sido eliminado correctamente.");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al eliminar el producto.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un producto para eliminar.");
            alert.showAndWait();
        }
    }
}
