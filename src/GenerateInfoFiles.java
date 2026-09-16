import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    public static void main(String[] args) {
        System.out.println("=== Generando archivos de prueba ===\n");

        System.out.println("Paso 1: Generando vendedores...");
        createSalesManInfoFile(5);
        System.out.println();

        System.out.println("Paso 2: Generando productos...");
        createProductsFile(10);
        System.out.println();

        System.out.println("Paso 3: Generando archivos de ventas...");

        long[] idsVendedores = {123456789L, 987654321L, 111222333L, 555666777L, 999888777L};
        String[] nombresVendedores = {"Juan", "María", "Carlos", "Ana", "Pedro"};

        for (int i = 0; i < idsVendedores.length; i++) {
            createSalesManFile(8, nombresVendedores[i], idsVendedores[i]);
        }

        System.out.println("\n=== Archivos generados exitosamente ===");
    }

    // metodo que genera el archivo de los vendors
    public static void createSalesManInfoFile(int salesmanCount) {
        try {
            FileWriter writer = new FileWriter("vendedores.txt");

            // tipos de documento disponibles para los vendors
            String[] tipos = {"CC", "TI", "CE"};
            // nombres de los vendeores
            String[] nombres = {"Juan", "María", "Carlos", "Ana", "Pedro", "Laura", "Luis", "Patricia"};
            // apellidos
            String[] apellidos = {"Pérez", "García", "López", "Martínez", "Rodríguez", "Hernández", "González", "Flores"};

            Random random = new Random();

            for (int i = 0; i < salesmanCount; i++) {
                String tipo = tipos[random.nextInt(tipos.length)];
                long numeroDocumento = 100000000L + random.nextLong(900000000L);
                String nombre = nombres[random.nextInt(nombres.length)];
                String apellido = apellidos[random.nextInt(apellidos.length)];

                // aquí se escribe la línea en formato así == tipo;numero;nombre;apellido
                writer.write(tipo + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n");
            }

            writer.close();
            System.out.println("Archivo vendedores.txt creado con " + salesmanCount + " vendedores");

        } catch (IOException e) {
            System.out.println("Error al crear archivo de vendedores: " + e.getMessage());
        }
    }
    // aquí finaliza el metodo de generar archivos de vendedores y breve

    // metodo que genera los archivos de productos
    public static void createProductsFile(int productsCount) {
        try {
            FileWriter writer = new FileWriter("productos.txt");

            String[] productNames = {"Laptop", "Mouse", "Teclado", "Monitor", "Cable USB",
                    "Audífonos", "Webcam", "Mousepad", "Adaptador", "Hub USB"};

            Random random = new Random();

            for (int i = 0; i < productsCount; i++) {
                // el ID va con formato P001, P002, etc (P es de producto jejeje)
                String idProducto = "P" + String.format("%03d", i + 1);
                String nombreProducto = productNames[random.nextInt(productNames.length)];
                // precio aleatorio entre 10000 y 2000000
                long precio = 10000 + random.nextLong(1990000);

                // escribimos == id;nombre;precio así mero como el de venderdores
                writer.write(idProducto + ";" + nombreProducto + ";" + precio + "\n");
            }

            writer.close();
            System.out.println("Archivo productos.txt creado con " + productsCount + " productos");

        } catch (IOException e) {
            System.out.println("Error al crear archivo de productos: " + e.getMessage());
        }
    }
    // aquí se finaliza el metodo de productos

    // metodo que genera archivo de ventas para cada vendedor
    public static void createSalesManFile(int randomSalesCount, String name, long id) {
        try {
            // el archivo se llama con el ID del vendedor
            String filename = id + "_ventas.txt";
            FileWriter writer = new FileWriter(filename);

            // estos IDs tienen que coincidir con los productos generados y breves
            String[] productIds = {"P001", "P002", "P003", "P004", "P005", "P006", "P007", "P008", "P009", "P010"};

            String tipoDocumento = "CC";

            Random random = new Random();

            for (int i = 0; i < randomSalesCount; i++) {
                String productoId = productIds[random.nextInt(productIds.length)];
                // cantidad entre 1 y 20 unidades
                int cantidad = 1 + random.nextInt(20);

                // escribimos == tipodoc;numero;producto;cantidad mismo anteriores
                writer.write(tipoDocumento + ";" + id + ";" + productoId + ";" + cantidad + "\n");
            }

            writer.close();
            System.out.println("Archivo " + filename + " creado para " + name + " con " + randomSalesCount + " ventas");

        } catch (IOException e) {
            System.out.println("Error al crear archivo de ventas: " + e.getMessage());
        }
    }
    // Fin del metodo y sale

}