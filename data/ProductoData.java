package cr.ac.ucr.paraiso.progra1.c2424.proyecto2.data;

import cr.ac.ucr.paraiso.progra1.c2424.proyecto2.model.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductoData {
    private String rutaArchivo;

    public ProductoData() throws IOException {
        this.rutaArchivo = "D:\\Proyecto 2 Progra 1\\My stuff\\ARTICULOS.DAT";  // Ruta específica

        // Crear el archivo si no existe
        File file = new File(rutaArchivo);
        if (!file.exists()) {
            file.createNewFile(); // Si no existe, lo crea
        }
    }

    // Método para obtener el siguiente ID disponible
    private int obtenerSiguienteId() throws IOException {
        int maxId = 0;  // Empezamos con ID 0 como el mínimo posible
        List<String> productos = findAll();  // Obtenemos todos los productos

        for (String producto : productos) {
            String[] partes = producto.split(";");
            try {
                int id = Integer.parseInt(partes[0]);  // Parseamos solo la primera parte (ID numérico)
                if (id > maxId) {
                    maxId = id;  // Encontramos el ID más grande
                }
            } catch (NumberFormatException e) {
                // Si no puede parsear, significa que ese valor no es un ID numérico, así que lo ignoramos
                continue;
            }
        }
        return maxId + 1;  // El siguiente ID será el mayor + 1
    }

    // Método para insertar un producto con ID único
    public void insertar(Product product) throws IOException {
        int nuevoId = obtenerSiguienteId();  // Obtener el siguiente ID disponible

        // Guardamos el nuevo producto en el archivo
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            printWriter.print(nuevoId + ";");
            printWriter.print(product.getDescription() + ";");
            printWriter.print(product.getPrice() + ";");
            printWriter.println(product.getQuantity() + ";");
        }
    }

    // Método para obtener todos los productos desde el archivo
    public List<String> findAll() throws IOException {
        List<String> productos = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(rutaArchivo))) {
            while (scanner.hasNextLine()) {
                productos.add(scanner.nextLine());
            }
        }
        return productos;
    }

    // Método para eliminar un producto del archivo
    public void eliminarProducto(Product product) throws IOException {
        List<String> productos = findAll();
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (String productoString : productos) {
                String[] datos = productoString.split(";");
                if (!datos[0].equals(String.valueOf(product.getId()))) {  // Compara ID, si no es el producto, lo reescribe
                    printWriter.println(productoString);
                }
            }
        }
    }
}

