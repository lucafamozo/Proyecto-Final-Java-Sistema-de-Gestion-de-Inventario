/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.Categoria;
import java.io.Serializable;

/**
 *
 * @author Luca
 */

public abstract class Producto implements Comparable<Producto>, Serializable {

    private static final long serialVersionUID = 1L;
    
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private Categoria categoria;

    // Constructor completo
    public Producto(int id, String nombre, double precio, int stock, Categoria categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Constructor sin categoria
    public Producto(int id, String nombre, double precio, int stock) {
        this(id, nombre, precio, stock, Categoria.SIN_CATEGORIA);
    }

    public Producto(int id, String nombre) {
        this(id, nombre, 0.0, 0, Categoria.SIN_CATEGORIA);
    }

    // Método abstracto que cada subclase implementa
    public abstract double calcularPrecioFinal();

    // Comparable — orden natural por id
    @Override
    public int compareTo(Producto p) {
        return Integer.compare(this.id, p.id);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " | Precio: $" + precio +
               " | Stock: " + stock + " | Categoría: " + categoria;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}