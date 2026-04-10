/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
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
    private int contadorId = 1;

    public GestorProductos() {
        this.lista = new ArrayList<>();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────

    @Override
    public void agregar(T item) {
        if (item.getId() == 0) {
            // Producto nuevo desde la UI
            item.setId(contadorId++);
        } else {
            // Producto cargado desde archivo, actualizamos el contador
            if (item.getId() >= contadorId) {
                contadorId = item.getId() + 1;
            }
        }
        lista.add(item);
    }

    @Override
    public void eliminar(int id) throws ProductoNoEncontradoException, StockInsuficienteException {
        T producto = buscarPorId(id);
        if (producto.getStock() == 0) {
            throw new StockInsuficienteException(producto.getNombre(), producto.getStock());
        }
        lista.remove(producto);
    }

    @Override
    public void actualizar(T item) throws ProductoNoEncontradoException {
        T existente = buscarPorId(item.getId());
        int index = lista.indexOf(existente);
        lista.set(index, item);
    }

    @Override
    public List<T> listar() {
        return new ArrayList<>(lista);
    }

    public void limpiar() {
        lista.clear();
        contadorId = 1;
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
            for (T item : lista) {
                if (item.getId() == p.getId()) {
                    resultado.add(item);
                    break;
                }
            }
        }
        return resultado;
    }

    // ? super → límite inferior: agrega elementos a una lista de T o supertipo
    public void agregarTodos(List<? super T> destino) {
        destino.addAll(lista);
    }

    // ── INTERFACES FUNCIONALES ────────────────────────────────────────────────

    public void modificar(Consumer<T> accion) {
        for (T item : lista) {
            accion.accept(item);
        }
    }

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