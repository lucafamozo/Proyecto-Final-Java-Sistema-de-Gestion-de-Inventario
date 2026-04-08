/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import modelo.Producto;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luca
 */
public class JsonManager {

    // Adaptador para que Gson pueda manejar LocalDate
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, t, ctx) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, t, ctx) ->
                            LocalDate.parse(json.getAsString()))
            .registerTypeAdapter(Producto.class, new ProductoAdapter())
            .setPrettyPrinting()
            .create();

    public void guardar(List<Producto> lista, String ruta) {
        try (Writer writer = new FileWriter(ruta)) {
            gson.toJson(lista, writer);
            System.out.println("JSON guardado en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    public List<Producto> cargar(String ruta) {
        try (Reader reader = new FileReader(ruta)) {
            Type tipo = new TypeToken<List<Producto>>() {}.getType();
            return gson.fromJson(reader, tipo);
        } catch (IOException e) {
            System.out.println("Error al cargar JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}