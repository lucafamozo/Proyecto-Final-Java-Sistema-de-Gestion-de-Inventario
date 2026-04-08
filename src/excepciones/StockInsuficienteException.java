/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Luca
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String nombre, int stockActual) {
        super("Stock insuficiente para '" + nombre + "'. Stock actual: " + stockActual);
    }
}