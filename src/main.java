import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class main {

    // clase para guardar info de vendedor
    public static class Vendedor implements Comparable<Vendedor> {
        String tipoDoc;
        long numDoc;
        String nombre;
        String apellido;
        long dineroRecaudado = 0;

        public Vendedor(String tipoDoc, long numDoc, String nombre, String apellido) {
            this.tipoDoc = tipoDoc;
            this.numDoc = numDoc;
            this.nombre = nombre;
            this.apellido = apellido;
        }

        // para ordenar por dinero de mayor a menor
        @Override
        public int compareTo(Vendedor otro) {
            return Long.compare(otro.dineroRecaudado, this.dineroRecaudado);
        }
    }

    // clase para guardar info de producto
    public static class Producto implements Comparable<Producto> {
        String idProducto;
        String nombre;
        long precio;
        int cantidadVendida = 0;

        public Producto(String idProducto, String nombre, long precio) {
            this.idProducto = idProducto;
            this.nombre = nombre;
            this.precio = precio;
        }

        // para ordenar por cantidad de mayor a menor
        @Override
        public int compareTo(Producto otro) {
            return Integer.compare(otro.cantidadVendida, this.cantidadVendida);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Leyendo archivos y procesando datos ===\n");

        // listas donde se guarda los datos
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        HashMap<Long, Vendedor> vendedoresMap = new HashMap<>();
        ArrayList<Producto> productos = new ArrayList<>();
        HashMap<String, Producto> productosMap = new HashMap<>();

        try {
            // aquí son 4 pasos, creo que así entendemos mejor:
            // PASO 1: Leer archivo de vendedores
            System.out.println("Paso 1: Leyendo vendedores");
            leerVendedores("vendedores.txt", vendedores, vendedoresMap);
            System.out.println("Se cargaron " + vendedores.size() + " vendedores\n");

            // PASO 2: Leer archivo de productos
            System.out.println("Paso 2: Leyendo productos.txt...");
            leerProductos("productos.txt", productos, productosMap);
            System.out.println("Se cargaron " + productos.size() + " productos\n");

            // PASO 3: Leer archivos de ventas y calcular dinero
            System.out.println("Paso 3: Leyendo archivos de ventas...");
            for (Vendedor v : vendedores) {
                String nombreArchivoVentas = v.numDoc + "_ventas.txt";
                leerVentasYProcesar(nombreArchivoVentas, v, vendedoresMap, productosMap);
            }
            System.out.println("Ventas procesadas\n");

            // PASO 4: Ordenar y generar reportes
            System.out.println("Paso 4: Generando reportes...");
            Collections.sort(vendedores);
            Collections.sort(productos);

            generarReporteVendedores("reporte_vendedores.csv", vendedores);
            generarReporteProductos("reporte_productos.csv", productos);

            System.out.println("Reporte de vendedores generado");
            System.out.println("Reporte de productos generado\n");

            System.out.println("=== Proceso completado exitosamente.. Melo===");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // metodo que lee el archivo de vendedores
    public static void leerVendedores(String nombreArchivo, ArrayList<Vendedor> vendedores,
                                      HashMap<Long, Vendedor> vendedoresMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
        String linea;

        // saltamos el encabezado
        reader.readLine();

        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(";");
            String tipoDoc = datos[0];
            long numDoc = Long.parseLong(datos[1]);
            String nombre = datos[2];
            String apellido = datos[3];

            Vendedor v = new Vendedor(tipoDoc, numDoc, nombre, apellido);
            vendedores.add(v);
            vendedoresMap.put(numDoc, v);
        }

        reader.close();
    }

    // metodo que lee el archivo de productos
    public static void leerProductos(String nombreArchivo, ArrayList<Producto> productos,
                                     HashMap<String, Producto> productosMap) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
        String linea;

        // saltamos el encabezado
        reader.readLine();

        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(";");
            String idProducto = datos[0];
            String nombre = datos[1];
            long precio = Long.parseLong(datos[2]);

            Producto p = new Producto(idProducto, nombre, precio);
            productos.add(p);
            productosMap.put(idProducto, p);
        }

        reader.close();
    }

    // metodo que lee las ventas y calcula dinero por vendedor y cantidad por producto
    public static void leerVentasYProcesar(String nombreArchivo, Vendedor vendedor,
                                           HashMap<Long, Vendedor> vendedoresMap, HashMap<String, Producto> productosMap)
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nombreArchivo));
        String linea;

        // saltamos el encabezado
        reader.readLine();

        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(";");
            String idProducto = datos[2];
            int cantidad = Integer.parseInt(datos[3]);

            // buscamos el producto en el mapa
            Producto p = productosMap.get(idProducto);
            if (p != null) {
                // sumamos la cantidad vendida
                p.cantidadVendida += cantidad;

                // calculamos el dinero recaudado
                long dineroVenta = cantidad * p.precio;
                vendedor.dineroRecaudado += dineroVenta;
            }
        }

        reader.close();
    }

    // metodo que genera el reporte de vendedores
    public static void generarReporteVendedores(String nombreArchivo, ArrayList<Vendedor> vendedores)
            throws IOException {
        FileWriter writer = new FileWriter(nombreArchivo);

        // escribimos el encabezado
        writer.write("TipoDocumento;NúmeroDocumento;Nombre;Apellido;DineroRecaudado\n");

        // escribimos cada vendedor
        for (Vendedor v : vendedores) {
            writer.write(v.tipoDoc + ";" + v.numDoc + ";" + v.nombre + ";" + v.apellido + ";"
                    + v.dineroRecaudado + "\n");
        }

        writer.close();
    }

    // metodo que genera el reporte de productos
    public static void generarReporteProductos(String nombreArchivo, ArrayList<Producto> productos)
            throws IOException {
        FileWriter writer = new FileWriter(nombreArchivo);

        // escribimos el encabezado
        writer.write("IDProducto;Nombre;Precio;CantidadVendida\n");

        // escribimos cada producto
        for (Producto p : productos) {
            writer.write(p.idProducto + ";" + p.nombre + ";" + p.precio + ";" + p.cantidadVendida + "\n");
        }

        writer.close();
    }

}