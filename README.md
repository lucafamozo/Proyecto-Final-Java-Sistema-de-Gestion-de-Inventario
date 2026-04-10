# Inventario de Productos (Java)

## Sobre mí

Soy **Luca Famozo**. Este repositorio corresponde al **Final de Programación II (Java)** de la **UTN Avellaneda**.

## Resumen

Aplicación de escritorio para **gestionar productos** (inventario/stock) desarrollada en Java con interfaz gráfica JavaFX.

Permite administrar tres tipos de productos: **Alimenticios**, **Electrónicos** y **de Limpieza**, cada uno con sus propias características y lógica de precio final.

### Funcionalidades principales

- **CRUD**: agregar, listar, editar y eliminar productos desde la interfaz gráfica.
- **Ordenamiento**: por criterio natural (`Comparable`) y por **precio** o **nombre** (`Comparator`).
- **Filtrado**: búsqueda por nombre con **wildcards** (`? extends`, `? super`).
- **Cambios masivos**: aumentar precio 10% (`Consumer`) y aplicar descuento 5% (`Function`).
- **Persistencia**: guardado y carga en **DAT** (serialización), **CSV** y **JSON**.
- **Exportación TXT**: reporte legible con encabezado exportable desde la UI.
- **Excepciones propias**: `ProductoNoEncontradoException` y `StockInsuficienteException`.

## Capturas de la interfaz

> Agregar capturas de pantalla de la aplicación aquí.

---

## Diagrama UML

![UML](docs/Diagrama-UML-Final.png)

---

## Estructura del proyecto

```
src/
├── comparadores/    → ComparadorPorPrecio, ComparadorPorNombre
├── enums/           → Categoria
├── excepciones/     → ProductoNoEncontradoException, StockInsuficienteException
├── gestor/          → GestorProductos<T extends Producto>
├── interfaces/      → Crud<T>, Descontable
├── modelo/          → Producto (abstracta), ProductoAlimenticio, ProductoElectronico, ProductoLimpieza
├── persistencia/    → CsvManager, JsonManager, Serializador, TxtExporter
├── ui/              → MainApp, ProductoController, DialogoProducto
└── Main.java
```

---

## Archivos generados

Se generan en la carpeta raíz del proyecto al usar la aplicación:

| Archivo | Descripción |
|---|---|
| `productos.dat` | Lista serializada de productos |
| `productos.csv` | Exportación/importación en formato CSV |
| `productos.json` | Exportación/importación en formato JSON |
| `productos.txt` | Reporte legible exportado desde la UI |

---

## Cómo ejecutar

1. Abrí el proyecto en **NetBeans**.
2. Configurá el **JavaFX SDK** en el proyecto (Module Path + VM Options).
3. Ejecutá la clase principal `Main.java`.

---

## Tecnologías

- Java 21
- JavaFX
- Apache NetBeans 22
- Gson (para JSON)
- Colecciones genéricas, Iterator, Wildcards
- Serialización y manejo de archivos (DAT / CSV / JSON / TXT)

---

## Autor

**Luca Famozo** — UTN Avellaneda, Programación II, 2024
