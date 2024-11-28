package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.controller;

import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.data.ClienteData;
import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.model.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import java.io.IOException;

public class ClientController {

    private VBox layout;
    private ObservableList<Client> clientesObservableList;

    public ClientController() {
        createUI();
    }

    // Método para crear la UI de inserción
    public void createUI() {
        layout = new VBox(10);
        layout.setPadding(new Insets(15));

        // Titulo
        Label lblTitulo = new Label("Insertar un nuevo cliente");

        // Campos para el formulario
        Label lblNombre = new Label("Nombre");
        TextField tfNombre = new TextField();
        Label lblTelefono = new Label("Teléfono");
        TextField tfTelefono = new TextField();
        Label lblDireccion = new Label("Dirección");
        TextField tfDireccion = new TextField();

        // Crear el GridPane
        GridPane pane = new GridPane();
        pane.add(lblNombre, 0, 0);
        pane.add(tfNombre, 1, 0);
        pane.add(lblTelefono, 0, 1);
        pane.add(tfTelefono, 1, 1);
        pane.add(lblDireccion, 0, 2);
        pane.add(tfDireccion, 1, 2);

        // Botones
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(e -> handleBtnInsertar(tfNombre.getText(), tfTelefono.getText(), tfDireccion.getText()));
        Button btnCancelar = new Button("Cancelar");
        pane.add(btnGuardar, 0, 3);
        pane.add(btnCancelar, 1, 3);

        this.layout.getChildren().addAll(lblTitulo, pane);
    }

    // Método para manejar la inserción de un cliente
    public void handleBtnInsertar(String nombre, String telefono, String direccion) {
        try {
            ClienteData clienteData = new ClienteData();
            Client newClient = new Client(null, nombre, telefono, direccion);  // El ID será generado automáticamente
            clienteData.insertar(newClient);

            // Mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("El cliente ha sido insertado correctamente.");
            alert.showAndWait();
        } catch (IOException e) {
            // Manejo de errores
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un error al guardar el cliente.");
            alert.showAndWait();
        }
    }

    // Método para obtener el layout de inserción
    public VBox getLayout() {
        return layout;
    }

    // Método para mostrar la lista de clientes y permitir su eliminación
    public VBox getLayoutParaEliminar() {
        layout = new VBox(10);
        layout.setPadding(new Insets(15));

        // Crear la tabla de clientes
        TableView<Client> table = new TableView<>();
        TableColumn<Client, String> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        TableColumn<Client, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<Client, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        TableColumn<Client, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        table.getColumns().addAll(colId, colNombre, colTelefono, colDireccion);

        // Cargar la lista de clientes
        try {
            ClienteData clienteData = new ClienteData();
            clientesObservableList = FXCollections.observableArrayList();  // Inicializamos la lista
            for (String clienteString : clienteData.findAll()) {
                String[] datos = clienteString.split(";");
                // Ahora pasamos el ID, nombre, teléfono y dirección correctamente
                Client cliente = new Client(datos[0], datos[1], datos[2], datos[3]);  // Usamos el ID del archivo
                clientesObservableList.add(cliente);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setItems(clientesObservableList);

        // Botón eliminar cliente
        Button btnEliminar = new Button("Eliminar Cliente");
        btnEliminar.setOnAction(e -> handleEliminarCliente(table));

        layout.getChildren().addAll(table, btnEliminar);

        return layout;
    }

    // Método para manejar la eliminación de un cliente
    private void handleEliminarCliente(TableView<Client> table) {
        Client selectedClient = table.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            try {
                ClienteData clienteData = new ClienteData();
                // Eliminar el cliente del archivo
                clienteData.eliminarCliente(selectedClient);
                // Actualizar la lista
                clientesObservableList.remove(selectedClient);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("El cliente ha sido eliminado correctamente.");
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Hubo un error al eliminar el cliente.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Debe seleccionar un cliente para eliminar.");
            alert.showAndWait();
        }
    }
}
