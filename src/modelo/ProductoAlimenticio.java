/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.Categoria;
import interfaces.Descontable;
import java.time.LocalDate;

/**
 *
 * @author Luca
 */
public class ProductoAlimenticio extends Producto implements Descontable {
    
    private static final long serialVersionUID = 1L;

    private LocalDate fechaVencimiento;
    private double peso;

    // Constructor completo
    public ProductoAlimenticio(int id, String nombre, double precio, int stock,
                                Categoria categoria, LocalDate fechaVencimiento, double peso) {
        super(id, nombre, precio, stock, categoria);
        this.fechaVencimiento = fechaVencimiento;
        this.peso = peso;
    }

    // Constructor sin peso
    public ProductoAlimenticio(int id, String nombre, double precio, int stock,
                                Categoria categoria, LocalDate fechaVencimiento) {
        this(id, nombre, precio, stock, categoria, fechaVencimiento, 0.0);
    }

    // Constructor mínimo
    public ProductoAlimenticio(int id, String nombre, double precio) {
        this(id, nombre, precio, 0, Categoria.SIN_CATEGORIA, LocalDate.now(), 0.0);
    }

    // Si está por vencer (menos de 7 días) aplica 20% de descuento
    @Override
    public double calcularPrecioFinal() {
        if (fechaVencimiento.isBefore(LocalDate.now().plusDays(7))) {
            return getPrecio() * 0.80;
        }
        return getPrecio();
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        setPrecio(getPrecio() - (getPrecio() * porcentaje / 100));
    }

    @Override
    public String toString() {
        return super.toString() + " | Vence: " + fechaVencimiento + " | Peso: " + peso + "kg";
    }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
}