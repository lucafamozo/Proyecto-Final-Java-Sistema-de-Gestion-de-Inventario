/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import enums.Categoria;
import interfaces.Descontable;

/**
 *
 * @author Luca
 */
public class ProductoElectronico extends Producto implements Descontable {

    private String marca;
    private int garantiaMeses;

    // Constructor completo
    public ProductoElectronico(int id, String nombre, double precio, int stock,
                                Categoria categoria, String marca, int garantiaMeses) {
        super(id, nombre, precio, stock, categoria);
        this.marca = marca;
        this.garantiaMeses = garantiaMeses;
    }

    // Constructor sin garantia
    public ProductoElectronico(int id, String nombre, double precio, int stock,
                                Categoria categoria, String marca) {
        this(id, nombre, precio, stock, categoria, marca, 12);
    }

    // Constructor mínimo
    public ProductoElectronico(int id, String nombre, double precio) {
        this(id, nombre, precio, 0, Categoria.ELECTRONICA, "Sin marca", 12);
    }

    // Electrónicos con más de 24 meses de garantía tienen 10% extra de precio
    @Override
    public double calcularPrecioFinal() {
        if (garantiaMeses > 24) {
            return getPrecio() * 1.10;
        }
        return getPrecio();
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        setPrecio(getPrecio() - (getPrecio() * porcentaje / 100));
    }

    @Override
    public String toString() {
        return super.toString() + " | Marca: " + marca + " | Garantía: " + garantiaMeses + " meses";
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getGarantiaMeses() { return garantiaMeses; }
    public void setGarantiaMeses(int garantiaMeses) { this.garantiaMeses = garantiaMeses; }
}
