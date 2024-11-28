package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.model;

import javafx.beans.property.*;

public class Client {
    private StringProperty id;
    private StringProperty name;
    private StringProperty phone;
    private StringProperty address;

    // Constructor
    public Client(String id, String name, String phone, String address) {
        this.id = new SimpleStringProperty(id);  // Usamos StringProperty para el ID
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.address = new SimpleStringProperty(address);
    }

    // Getters y Setters
    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public StringProperty addressProperty() {
        return address;
    }

    // Métodos para obtener el valor de cada propiedad
    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getAddress() {
        return address.get();
    }
}
