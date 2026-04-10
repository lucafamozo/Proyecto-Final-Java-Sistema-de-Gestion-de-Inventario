/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import comparadores.ComparadorPorNombre;
import comparadores.ComparadorPorPrecio;
import excepciones.ProductoNoEncontradoException;
import excepciones.StockInsuficienteException;
import gestor.GestorProductos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.Producto;
import persistencia.*;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Luca
 */
public class ProductoController {

    private final Stage stage;
    private final GestorProductos<Producto> gestor = new GestorProductos<>();
    private final CsvManager csvManager     = new CsvManager();
    private final JsonManager jsonManager   = new JsonManager();
    private final Serializador serializador = new Serializador();
    private final TxtExporter txtExporter   = new TxtExporter();

    private TableView<Producto> tabla;
    private TextField txtFiltro;
    private ComboBox<String> cmbOrden;

    public ProductoController(Stage stage) {
        this.stage = stage;
    }

    public void mostrar() {
        stage.setTitle("Gestor de Productos");

        // ── TABLA ─────────────────────────────────────────────────────────────
        tabla = new TableView<>();

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(150);

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setPrefWidth(100);

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(80);

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(120);

        TableColumn<Producto, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getClass().getSimpleName()));
        colTipo.setPrefWidth(180);

        TableColumn<Producto, String> colPrecioFinal = new TableColumn<>("Precio Final");
        colPrecioFinal.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("$%.2f", data.getValue().calcularPrecioFinal())));
        colPrecioFinal.setPrefWidth(100);

        // Formatear columna precio
        TableColumn<Producto, String> colPrecioFormato = new TableColumn<>("Precio");
        colPrecioFormato.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.format("$%.2f", data.getValue().getPrecio())));
        colPrecioFormato.setPrefWidth(100);

        tabla.getColumns().addAll(colId, colNombre, colPrecioFormato, colStock,
                                   colCategoria, colTipo, colPrecioFinal);
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ── PANEL SUPERIOR ────────────────────────────────────────────────────
        txtFiltro = new TextField();
        txtFiltro.setPromptText("Buscar por nombre...");
        txtFiltro.setPrefWidth(200);

        Button btnFiltrar = new Button("Filtrar");
        btnFiltrar.setOnAction(e -> filtrar());

        Button btnMostrarTodos = new Button("Mostrar todos");
        btnMostrarTodos.setOnAction(e -> mostrarTodos());

        cmbOrden = new ComboBox<>();
        cmbOrden.getItems().addAll("ID (natural)", "Precio", "Nombre");
        cmbOrden.getSelectionModel().selectFirst();

        Button btnOrdenar = new Button("Ordenar");
        btnOrdenar.setOnAction(e -> ordenar());

        HBox panelSuperior = new HBox(10,
                new Label("Filtrar:"), txtFiltro, btnFiltrar, btnMostrarTodos,
                new Label("Ordenar por:"), cmbOrden, btnOrdenar);
        panelSuperior.setPadding(new Insets(10));

        // ── PANEL INFERIOR: CRUD ──────────────────────────────────────────────
        Button btnAgregar  = new Button("Agregar");
        Button btnEditar   = new Button("Editar");
        Button btnEliminar = new Button("Eliminar");
        Button btnAumentar = new Button("Aumentar precio 10%");
        Button btnTransformar = new Button("Aplicar descuento 5%");

        btnAgregar.setOnAction(e -> abrirDialogoAgregar());
        btnEditar.setOnAction(e -> abrirDialogoEditar());
        btnEliminar.setOnAction(e -> eliminar());
        btnAumentar.setOnAction(e -> aumentarPrecio());
        btnTransformar.setOnAction(e -> {
            gestor.transformar(p -> {
                p.setPrecio(p.getPrecio() * 0.95);
                return p;
            });
            refrescar();
            info("Descuento del 5% aplicado a todos los productos.");
        });

        HBox panelCrud = new HBox(10,
            btnAgregar, btnEditar, btnEliminar,
            new Separator(javafx.geometry.Orientation.VERTICAL),
            btnAumentar, btnTransformar);
        panelCrud.setPadding(new Insets(5, 10, 5, 10));

        // ── PANEL INFERIOR: PERSISTENCIA ──────────────────────────────────────
        Button btnGuardarCsv  = new Button("Guardar CSV");
        Button btnCargarCsv   = new Button("Cargar CSV");
        Button btnGuardarJson = new Button("Guardar JSON");
        Button btnCargarJson  = new Button("Cargar JSON");
        Button btnGuardarDat  = new Button("Guardar .dat");
        Button btnCargarDat   = new Button("Cargar .dat");
        Button btnExportarTxt = new Button("Exportar TXT");

        btnGuardarCsv.setOnAction(e  -> { csvManager.guardar(gestor.listar(), "productos.csv");    info("CSV guardado."); });
        btnCargarCsv.setOnAction(e   -> { gestor.limpiar(); csvManager.cargar("productos.csv").forEach(gestor::agregar);   refrescar(); });
        btnGuardarJson.setOnAction(e -> { jsonManager.guardar(gestor.listar(), "productos.json");  info("JSON guardado."); });
        btnCargarJson.setOnAction(e  -> { gestor.limpiar(); jsonManager.cargar("productos.json").forEach(gestor::agregar); refrescar(); });
        btnGuardarDat.setOnAction(e  -> { serializador.guardar(gestor.listar(), "productos.dat");  info("DAT guardado."); });
        btnCargarDat.setOnAction(e   -> { gestor.limpiar(); serializador.cargar("productos.dat").forEach(gestor::agregar); refrescar(); });
        btnExportarTxt.setOnAction(e -> {
            List<Producto> listaAExportar = tabla.getItems();
            txtExporter.exportar(listaAExportar, "Reporte de Productos", "productos.txt");
            info("TXT exportado con " + listaAExportar.size() + " productos.");
        });

        HBox panelPersistencia = new HBox(10,
                btnGuardarCsv, btnCargarCsv,
                btnGuardarJson, btnCargarJson,
                btnGuardarDat, btnCargarDat,
                btnExportarTxt);
        panelPersistencia.setPadding(new Insets(5, 10, 10, 10));

        VBox panelInferior = new VBox(5, panelCrud, panelPersistencia);

        // ── LAYOUT PRINCIPAL ──────────────────────────────────────────────────
        BorderPane root = new BorderPane();
        root.setTop(panelSuperior);
        root.setCenter(tabla);
        root.setBottom(panelInferior);

        stage.setScene(new Scene(root, 950, 600));
        stage.show();

        refrescar();
    }

    // ── CRUD ──────────────────────────────────────────────────────────────────
    private void abrirDialogoAgregar() {
        new DialogoProducto(null, gestor).showAndWait();
        refrescar();
    }

    private void abrirDialogoEditar() {
        Producto sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Seleccioná un producto para editar."); return; }
        new DialogoProducto(sel, gestor).showAndWait();
        refrescar();
    }

    private void eliminar() {
        Producto sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) { alerta("Seleccioná un producto para eliminar."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar " + sel.getNombre() + "?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                gestor.eliminar(sel.getId());
                refrescar();
            } catch (StockInsuficienteException e) {
                alerta("No se puede eliminar: " + e.getMessage());
            } catch (ProductoNoEncontradoException e) {
                alerta("Producto no encontrado: " + e.getMessage());
            }
        }
    }

    // ── ORDENAR Y FILTRAR ─────────────────────────────────────────────────────
    private void ordenar() {
        switch (cmbOrden.getValue()) {
            case "Precio" -> gestor.ordenar(new ComparadorPorPrecio());
            case "Nombre" -> gestor.ordenar(new ComparadorPorNombre());
            default       -> gestor.ordenar(Producto::compareTo);
        }
        refrescar();
    }

    private void filtrar() {
        String texto = txtFiltro.getText().toLowerCase().trim();
        List<Producto> todos = gestor.listar();
        List<Producto> filtrados = gestor.filtrar(todos).stream()
                .filter(p -> p.getNombre().toLowerCase().contains(texto))
                .toList();
        tabla.setItems(FXCollections.observableArrayList(filtrados));
    }

    private void mostrarTodos() {
        txtFiltro.clear();
        refrescar();
    }

    // ── INTERFACES FUNCIONALES ────────────────────────────────────────────────
    private void aumentarPrecio() {
        gestor.modificar(p -> p.setPrecio(p.getPrecio() * 1.10));
        refrescar();
        info("Precio aumentado un 10% a todos los productos.");
    }

    // ── UTILIDADES ────────────────────────────────────────────────────────────
    private void refrescar() {
        ObservableList<Producto> obs = FXCollections.observableArrayList(gestor.listar());
        tabla.setItems(obs);
    }

    private void alerta(String msg) {
        new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK).showAndWait();
    }

    private void info(String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK).showAndWait();
    }
}