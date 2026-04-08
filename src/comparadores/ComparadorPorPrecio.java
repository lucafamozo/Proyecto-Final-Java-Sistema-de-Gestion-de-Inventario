/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comparadores;

import modelo.Producto;
import java.util.Comparator;

/**
 *
 * @author Luca
 */
public class ComparadorPorPrecio implements Comparator<Producto> {
    @Override
    public int compare(Producto p1, Producto p2) {
        return Double.compare(p1.getPrecio(), p2.getPrecio());
    }
}