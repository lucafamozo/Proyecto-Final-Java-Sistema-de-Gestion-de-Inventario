## Inventario de Productos (Java)

## Sobre mí

Soy **Luca Famozo**. Este repositorio corresponde al **Final de Programación II (Java)**.

## Resumen

Aplicación de escritorio para **gestionar productos** (inventario/stock) con:

- **CRUD**: alta, baja, modificación y listado.
- **Ordenamiento**: criterio natural (`Comparable`) + comparadores por **precio** y **stock**.
- **Filtrado**: con **wildcards** (`? extends`, `? super`).
- **Cambios masivos**: usando **interfaces funcionales** (`Consumer`) para modificar precio/stock.
- **Persistencia**: **DAT (serialización)**, **CSV** y **JSON**.
- **Exportación TXT**: reporte legible con encabezado.
- **Interfaz gráfica JavaFX**: operaciones y persistencia desde UI.

## Capturas / demostración

Agregar acá capturas de la UI JavaFX (recomendado):

- `docs/ui-1.png`
- `docs/ui-2.png`

---

## 📊 Diagrama UML

![UML](docs/Diagrama-UML-Final.png)

## Cómo ejecutar (NetBeans)

1. Abrir el proyecto en NetBeans.
2. Ejecutar la clase principal: `ui.Main`
   - Primero corre `ui.ConsoleDemo` (pruebas por consola).
   - Luego abre `ui.MainApp` (JavaFX).

Si JavaFX no abre, configurar el **JavaFX SDK** en el proyecto (Module Path + VM Options).

---

## 🧱 Estructura del proyecto

- **`model`**: clases principales (`Producto` abstracta + derivadas) y `Categoria` (enum)
- **`service`**: `Crud<T>`, `GestorProductos<T extends Producto>`, `ProductoIterator<T>`, `Descontable`
- **`comparator`**: `PrecioComparator`, `StockComparator`
- **`persistence`**: `Serializador`, `CsvManager`, `JsonManager`, `TxtExporter`
- **`exceptions`**: `ProductoNoEncontradoException`, `StockInvalidoException`
- **`ui`**: `ConsoleDemo`, `MainApp` (JavaFX), `Main` (entrypoint)

---

## Archivos generados (con ejemplos)

Se generan en la carpeta `data/` al ejecutar `ConsoleDemo` o desde la UI:

- **`productos.dat`**: lista serializada de productos.
- **`productos.csv`**: export/import en CSV con encabezado.
- **`productos.json`**: export/import en JSON.
- **`reporte_filtrado.txt`**: reporte TXT de una **lista filtrada**.
- **`reporte_ui.txt`**: reporte TXT exportado desde la interfaz.

---

## Tecnologías

* Java
* JavaFX
* NetBeans
* Colecciones (List, Iterator) + Genéricos
* Manejo de archivos (DAT/CSV/JSON/TXT)

---

## 🚀 Estado del proyecto

Listo para ejecutar.

---

## 👤 Autor

Luca Famozo
