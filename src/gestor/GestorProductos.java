/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import excepciones.ProductoNoEncontradoException;
import interfaces.Crud;
import modelo.Producto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author Luca
 */
public class GestorProductos<T extends Producto> implements Crud<T>, Iterable<T> {

    private List<T> lista;

    public GestorProductos() {
        this.lista = new ArrayList<>();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @Override
    public void agregar(T item) {
        lista.add(item);
    }

    @Override
    public void eliminar(int id) {
        try {
            T producto = buscarPorId(id);
            lista.remove(producto);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(T item) {
        try {
            T existente = buscarPorId(item.getId());
            int index = lista.indexOf(existente);
            lista.set(index, item);
        } catch (ProductoNoEncontradoException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    // ── BÚSQUEDA ──────────────────────────────────────────────────────────────

    public T buscarPorId(int id) throws ProductoNoEncontradoException {
        for (T item : lista) {
            if (item.getId() == id) {
                return item;
            }
        }
        throw new ProductoNoEncontradoException(id);
    }

    // ── ORDENAMIENTO ──────────────────────────────────────────────────────────

    public void ordenar(Comparator<T> comparador) {
        lista.sort(comparador);
    }

    // ── FILTRADO CON WILDCARDS ────────────────────────────────────────────────

    // ? extends → límite superior: acepta lista de T o cualquier subtipo
    public List<T> filtrar(List<? extends Producto> origen) {
        List<T> resultado = new ArrayList<>();
        for (Producto p : origen) {
            if (lista.contains(p)) {
                resultado.add((T) p);
            }
        }
        return resultado;
    }

    // ? super → límite inferior: agrega elementos a una lista de T o supertipo
    public void agregarTodos(List<? super T> destino) {
        destino.addAll(lista);
    }

    // ── INTERFACES FUNCIONALES ────────────────────────────────────────────────

    // Consumer: aplica una acción a cada producto (ej: imprimir, modificar precio)
    public void modificar(Consumer<T> accion) {
        for (T item : lista) {
            accion.accept(item);
        }
    }

    // Function: transforma cada producto y reemplaza en la lista
    public void transformar(Function<T, T> funcion) {
        for (int i = 0; i < lista.size(); i++) {
            lista.set(i, funcion.apply(lista.get(i)));
        }
    }

    // ── ITERATOR PERSONALIZADO ────────────────────────────────────────────────

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int indice = 0;

            @Override
            public boolean hasNext() {
                return indice < lista.size();
            }

            @Override
            public T next() {
                return lista.get(indice++);
            }
        };
    }
}