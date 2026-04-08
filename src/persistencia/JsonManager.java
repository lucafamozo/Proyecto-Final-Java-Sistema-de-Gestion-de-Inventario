/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import modelo.Producto;
import modelo.ProductoAlimenticio;
import modelo.ProductoElectronico;
import modelo.ProductoLimpieza;

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

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class,
                    (JsonSerializer<LocalDate>) (src, t, ctx) ->
                            new JsonPrimitive(src.toString()))
            .registerTypeAdapter(LocalDate.class,
                    (JsonDeserializer<LocalDate>) (json, t, ctx) ->
                            LocalDate.parse(json.getAsString()))
            .setPrettyPrinting()
            .create();

    public void guardar(List<Producto> lista, String ruta) {
        try (Writer writer = new FileWriter(ruta)) {
            JsonArray array = new JsonArray();

            for (Producto p : lista) {
                // Serializamos según el tipo concreto
                JsonObject obj;
                if (p instanceof ProductoAlimenticio) {
                    obj = gson.toJsonTree(p, ProductoAlimenticio.class).getAsJsonObject();
                } else if (p instanceof ProductoElectronico) {
                    obj = gson.toJsonTree(p, ProductoElectronico.class).getAsJsonObject();
                } else {
                    obj = gson.toJsonTree(p, ProductoLimpieza.class).getAsJsonObject();
                }
                // Agregamos el campo tipo manualmente
                obj.addProperty("tipo", p.getClass().getSimpleName());
                array.add(obj);
            }

            gson.toJson(array, writer);
            System.out.println("JSON guardado en: " + ruta);
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    public List<Producto> cargar(String ruta) {
        List<Producto> lista = new ArrayList<>();
        try (Reader reader = new FileReader(ruta)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                JsonElement tipoElem = obj.get("tipo");

                if (tipoElem == null) continue;

                String tipo = tipoElem.getAsString();
                Producto p = switch (tipo) {
                    case "ProductoAlimenticio" ->
                            gson.fromJson(obj, ProductoAlimenticio.class);
                    case "ProductoElectronico" ->
                            gson.fromJson(obj, ProductoElectronico.class);
                    case "ProductoLimpieza" ->
                            gson.fromJson(obj, ProductoLimpieza.class);
                    default -> null;
                };

                if (p != null) lista.add(p);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar JSON: " + e.getMessage());
        }
        return lista;
    }
}