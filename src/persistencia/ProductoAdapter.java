/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import com.google.gson.*;
import modelo.Producto;
import modelo.ProductoAlimenticio;
import modelo.ProductoElectronico;
import modelo.ProductoLimpieza;

import java.lang.reflect.Type;

/**
 *
 * @author Luca
 */
public class ProductoAdapter implements JsonDeserializer<Producto>, JsonSerializer<Producto> {

    @Override
    public JsonElement serialize(Producto p, Type type, JsonSerializationContext ctx) {
        JsonObject obj = ctx.serialize(p, p.getClass()).getAsJsonObject();
        obj.addProperty("tipo", p.getClass().getSimpleName());
        return obj;
    }

    @Override
    public Producto deserialize(JsonElement json, Type type, JsonDeserializationContext ctx)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String tipo = obj.get("tipo").getAsString();
        return switch (tipo) {
            case "ProductoAlimenticio" -> ctx.deserialize(obj, ProductoAlimenticio.class);
            case "ProductoElectronico" -> ctx.deserialize(obj, ProductoElectronico.class);
            case "ProductoLimpieza"    -> ctx.deserialize(obj, ProductoLimpieza.class);
            default -> throw new JsonParseException("Tipo desconocido: " + tipo);
        };
    }
}