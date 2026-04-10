/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.Categoria;

/**
 *
 * @author Luca
 */
public class ProductoLimpieza extends Producto {
    
    private static final long serialVersionUID = 1L;

    private String tipoUso;
    private boolean esBiodegradable;

    // Constructor completo
    public ProductoLimpieza(int id, String nombre, double precio, int stock,
                             Categoria categoria, String tipoUso, boolean esBiodegradable) {
        super(id, nombre, precio, stock, categoria);
        this.tipoUso = tipoUso;
        this.esBiodegradable = esBiodegradable;
    }

    // Constructor sin esBiodegradable
    public ProductoLimpieza(int id, String nombre, double precio, int stock,
                             Categoria categoria, String tipoUso) {
        this(id, nombre, precio, stock, categoria, tipoUso, false);
    }

    // Constructor mínimo
    public ProductoLimpieza(int id, String nombre, double precio) {
        this(id, nombre, precio, 0, Categoria.SIN_CATEGORIA, "General", false);
    }

    // Los biodegradables tienen un 5% adicional en el precio final
    @Override
    public double calcularPrecioFinal() {
        if (esBiodegradable) {
            return getPrecio() * 1.05;
        }
        return getPrecio();
    }

    @Override
    public String toString() {
        return super.toString() + " | Tipo de uso: " + tipoUso +
               " | Biodegradable: " + (esBiodegradable ? "Sí" : "No");
    }

    public String getTipoUso() { return tipoUso; }
    public void setTipoUso(String tipoUso) { this.tipoUso = tipoUso; }

    public boolean isEsBiodegradable() { return esBiodegradable; }
    public void setEsBiodegradable(boolean esBiodegradable) { this.esBiodegradable = esBiodegradable; }
}