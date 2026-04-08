/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import enums.Categoria;
import modelo.Producto;
import modelo.ProductoAlimenticio;
import modelo.ProductoElectronico;
import modelo.ProductoLimpieza;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca
 */
public class CsvManager {

    public void guardar(List<Producto> lista, String ruta) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            // Encabezado
            bw.write("tipo,id,nombre,precio,stock,categoria,extra1,extra2");
            bw.newLine();

            for (Producto p : lista) {
                String linea = construirLinea(p);
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("CSV guardado en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }

    private String construirLinea(Producto p) {
        String base = p.getClass().getSimpleName() + "," +
                      p.getId() + "," +
                      p.getNombre() + "," +
                      p.getPrecio() + "," +
                      p.getStock() + "," +
                      p.getCategoria();

        if (p instanceof ProductoAlimenticio pa) {
            return base + "," + pa.getFechaVencimiento() + "," + pa.getPeso();
        } else if (p instanceof ProductoElectronico pe) {
            return base + "," + pe.getMarca() + "," + pe.getGarantiaMeses();
        } else if (p instanceof ProductoLimpieza pl) {
            return base + "," + pl.getTipoUso() + "," + pl.isEsBiodegradable();
        }
        return base;
    }

    public List<Producto> cargar(String ruta) {
        List<Producto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            br.readLine(); // saltar encabezado
            while ((linea = br.readLine()) != null) {
                Producto p = parsearLinea(linea);
                if (p != null) lista.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar CSV: " + e.getMessage());
        }
        return lista;
    }

    private Producto parsearLinea(String linea) {
        String[] partes = linea.split(",");
        try {
            String tipo      = partes[0];
            int id           = Integer.parseInt(partes[1]);
            String nombre    = partes[2];
            double precio    = Double.parseDouble(partes[3]);
            int stock        = Integer.parseInt(partes[4]);
            Categoria cat    = Categoria.valueOf(partes[5]);

            return switch (tipo) {
                case "ProductoAlimenticio" -> new ProductoAlimenticio(
                        id, nombre, precio, stock, cat,
                        LocalDate.parse(partes[6]),
                        Double.parseDouble(partes[7]));
                case "ProductoElectronico" -> new ProductoElectronico(
                        id, nombre, precio, stock, cat,
                        partes[6],
                        Integer.parseInt(partes[7]));
                case "ProductoLimpieza" -> new ProductoLimpieza(
                        id, nombre, precio, stock, cat,
                        partes[6],
                        Boolean.parseBoolean(partes[7]));
                default -> null;
            };
        } catch (Exception e) {
            System.out.println("Error al parsear línea: " + linea);
            return null;
        }
    }
}