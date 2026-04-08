/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Luca
 */
public class ProductoNoEncontradoException extends Exception {
    public ProductoNoEncontradoException(int id) {
        super("No se encontró ningún producto con el ID: " + id);
    }
}