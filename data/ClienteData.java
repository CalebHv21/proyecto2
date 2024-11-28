package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.data;

import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.model.Client;  // Importación de Client
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteData {
    private String rutaArchivo;

    public ClienteData() throws IOException {
        this.rutaArchivo = "D:\\Proyecto 2 Progra 1\\My stuff\\CLIENTES.DAT";  // Ruta específica

        // Crear el archivo si no existe
        File file = new File(rutaArchivo);
        if (!file.exists()) {
            file.createNewFile(); // Si no existe, lo crea
        }
    }

    // Método para obtener el siguiente ID disponible como String
    private String obtenerSiguienteId() throws IOException {
        int maxId = 0;  // Empezamos con ID 0 como el mínimo posible
        List<String> clientes = findAll();  // Obtenemos todos los clientes

        for (String cliente : clientes) {
            String[] partes = cliente.split(";");
            int id = Integer.parseInt(partes[0]);  // Extraemos el ID numérico
            if (id > maxId) {
                maxId = id;  // Encontramos el ID más grande
            }
        }
        return String.valueOf(maxId + 1);  // Convertimos el siguiente ID a String
    }

    // Método para insertar un cliente con ID único
    public void insertar(Client client) throws IOException {
        String nuevoId = obtenerSiguienteId();  // Obtener el siguiente ID disponible

        // Guardamos el nuevo cliente en el archivo
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            printWriter.print(nuevoId + ";");
            printWriter.print(client.getName() + ";");
            printWriter.print(client.getPhone() + ";");
            printWriter.println(client.getAddress() + ";");
        }
    }

    // Método para obtener todos los clientes desde el archivo
    public List<String> findAll() throws IOException {
        List<String> clientes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(rutaArchivo))) {
            while (scanner.hasNextLine()) {
                clientes.add(scanner.nextLine());
            }
        }
        return clientes;
    }

    // Método para eliminar un cliente del archivo
    public void eliminarCliente(Client client) throws IOException {
        List<String> clientes = findAll();
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (String clienteString : clientes) {
                String[] datos = clienteString.split(";");
                if (!datos[0].equals(client.getId())) {  // Compara ID, si no es el cliente, lo reescribe
                    printWriter.println(clienteString);
                }
            }
        }
    }
}
