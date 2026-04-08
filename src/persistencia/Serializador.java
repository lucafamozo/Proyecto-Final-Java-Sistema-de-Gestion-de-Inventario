/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import modelo.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca
 */
public class Serializador {

    public void guardar(List<Producto> lista, String ruta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(lista);
            System.out.println("Datos serializados guardados en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al serializar: " + e.getMessage());
        }
    }

    public List<Producto> cargar(String ruta) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return (List<Producto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al deserializar: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}