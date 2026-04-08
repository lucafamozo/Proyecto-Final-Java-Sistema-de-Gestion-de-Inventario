/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import comparadores.ComparadorPorNombre;
import comparadores.ComparadorPorPrecio;
import enums.Categoria;
import gestor.GestorProductos;
import modelo.*;
import persistencia.*;

import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author Luca
 */
public class Main {

    public static void main(String[] args) {

        // ── CREAR GESTOR Y PRODUCTOS ──────────────────────────────────────────
        GestorProductos<Producto> gestor = new GestorProductos<>();

        gestor.agregar(new ProductoAlimenticio(1, "Leche", 500.0, 50,
                Categoria.ALIMENTOS, LocalDate.now().plusDays(3), 1.0));

        gestor.agregar(new ProductoAlimenticio(2, "Arroz", 300.0, 100,
                Categoria.ALIMENTOS, LocalDate.now().plusDays(30), 0.5));

        gestor.agregar(new ProductoElectronico(3, "Televisor", 80000.0, 10,
                Categoria.ELECTRONICA, "Samsung", 36));

        gestor.agregar(new ProductoElectronico(4, "Auriculares", 15000.0, 30,
                Categoria.ELECTRONICA, "Sony", 12));

        gestor.agregar(new ProductoLimpieza(5, "Detergente", 800.0, 200,
                Categoria.LIMPIEZA, "Hogar", true));

        gestor.agregar(new ProductoLimpieza(6, "Lavandina", 600.0, 150,
                Categoria.LIMPIEZA, "Desinfección", false));

        // ── LISTAR ────────────────────────────────────────────────────────────
        System.out.println("======= LISTA COMPLETA =======");
        for (Producto p : gestor) {
            System.out.println(p);
        }

        // ── ORDENAR POR PRECIO ────────────────────────────────────────────────
        System.out.println("\n======= ORDENADO POR PRECIO =======");
        gestor.ordenar(new ComparadorPorPrecio());
        for (Producto p : gestor) {
            System.out.println(p.getNombre() + " → $" + p.getPrecio());
        }

        // ── ORDENAR POR NOMBRE ────────────────────────────────────────────────
        System.out.println("\n======= ORDENADO POR NOMBRE =======");
        gestor.ordenar(new ComparadorPorNombre());
        for (Producto p : gestor) {
            System.out.println(p.getNombre());
        }

        // ── PRECIO FINAL (polimorfismo) ───────────────────────────────────────
        System.out.println("\n======= PRECIO FINAL =======");
        for (Producto p : gestor) {
            System.out.println(p.getNombre() + " → $" + p.calcularPrecioFinal());
        }

        // ── INTERFACES FUNCIONALES ────────────────────────────────────────────
        System.out.println("\n======= AUMENTO DE PRECIO 10% (Consumer) =======");
        gestor.modificar(p -> p.setPrecio(p.getPrecio() * 1.10));
        for (Producto p : gestor) {
            System.out.println(p.getNombre() + " → $" + p.getPrecio());
        }

        // ── ELIMINAR ──────────────────────────────────────────────────────────
        System.out.println("\n======= ELIMINAR ID 4 =======");
        gestor.eliminar(4);
        for (Producto p : gestor) {
            System.out.println(p.getNombre());
        }

        // ── ACTUALIZAR ────────────────────────────────────────────────────────
        System.out.println("\n======= ACTUALIZAR ID 1 =======");
        ProductoAlimenticio actualizado = new ProductoAlimenticio(1, "Leche Entera",
                550.0, 60, Categoria.ALIMENTOS, LocalDate.now().plusDays(10), 1.0);
        gestor.actualizar(actualizado);
        try {
            System.out.println(gestor.buscarPorId(1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // ── WILDCARDS: agregarTodos ───────────────────────────────────────────
        System.out.println("\n======= WILDCARD agregarTodos =======");
        List<Producto> copia = new java.util.ArrayList<>();
        gestor.agregarTodos(copia);
        System.out.println("Productos copiados: " + copia.size());

        // ── PERSISTENCIA CSV ──────────────────────────────────────────────────
        System.out.println("\n======= GUARDANDO CSV =======");
        CsvManager csv = new CsvManager();
        csv.guardar(gestor.listar(), "productos.csv");

        System.out.println("\n======= CARGANDO CSV =======");
        List<Producto> desdeCsv = csv.cargar("productos.csv");
        desdeCsv.forEach(System.out::println);

        // ── PERSISTENCIA JSON ─────────────────────────────────────────────────
        System.out.println("\n======= GUARDANDO JSON =======");
        JsonManager json = new JsonManager();
        json.guardar(gestor.listar(), "productos.json");

        System.out.println("\n======= CARGANDO JSON =======");
        List<Producto> desdeJson = json.cargar("productos.json");
        desdeJson.forEach(System.out::println);

        // ── SERIALIZACIÓN ─────────────────────────────────────────────────────
        System.out.println("\n======= SERIALIZANDO =======");
        Serializador serial = new Serializador();
        serial.guardar(gestor.listar(), "productos.dat");

        System.out.println("\n======= DESERIALIZANDO =======");
        List<Producto> desdeDat = serial.cargar("productos.dat");
        desdeDat.forEach(System.out::println);

        // ── EXPORTAR TXT ──────────────────────────────────────────────────────
        System.out.println("\n======= EXPORTANDO TXT =======");
        TxtExporter txt = new TxtExporter();
        txt.exportar(gestor.listar(), "Reporte de Productos", "productos.txt");
    }
}