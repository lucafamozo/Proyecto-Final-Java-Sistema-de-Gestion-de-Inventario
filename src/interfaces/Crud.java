/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.List;

/**
 *
 * @author Luca
 */
public interface Crud<T> {
    void agregar(T item);
    void eliminar(int id);
    void actualizar(T item);
    List<T> listar();
}
