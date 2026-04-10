/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import enums.Categoria;
import gestor.GestorProductos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import modelo.*;

import java.time.LocalDate;


/**
 *
 * @author Luca
 */
public class DialogoProducto extends Dialog<Producto> {

    private final Producto productoExistente;

    // ── CAMPOS COMUNES ────────────────────────────────────────────────────────
    private final TextField txtNombre   = new TextField();
    private final TextField txtPrecio   = new TextField();
    private final TextField txtStock    = new TextField();
    private final ComboBox<Categoria> cmbCategoria = new ComboBox<>();
    private final ComboBox<String>    cmbTipo      = new ComboBox<>();

    // ── CAMPOS ESPECÍFICOS ────────────────────────────────────────────────────
    private final DatePicker dpFechaVencimiento = new DatePicker();
    private final TextField  txtPeso            = new TextField();

    private final TextField txtMarca         = new TextField();
    private final TextField txtGarantiaMeses = new TextField();

    private final TextField txtTipoUso       = new TextField();
    private final CheckBox  chkBiodegradable = new CheckBox("Es biodegradable");

    // ── PANEL DINÁMICO ────────────────────────────────────────────────────────
    private final VBox panelEspecifico = new VBox(8);

    public DialogoProducto(Producto productoExistente, GestorProductos<Producto> gestor) {
        this.productoExistente = productoExistente;

        setTitle(productoExistente == null ? "Agregar Producto" : "Editar Producto");

        cmbTipo.getItems().addAll("ProductoAlimenticio", "ProductoElectronico", "ProductoLimpieza");
        cmbTipo.getSelectionModel().selectFirst();

        if (productoExistente != null) {
            txtNombre.setText(productoExistente.getNombre());
            txtPrecio.setText(String.valueOf(productoExistente.getPrecio()));
            txtStock.setText(String.valueOf(productoExistente.getStock()));
            cmbTipo.setValue(productoExistente.getClass().getSimpleName());
            cmbTipo.setDisable(true);
            cargarCamposEspecificos(productoExistente);
        }

        cmbTipo.setOnAction(e -> actualizarPanelEspecifico());
        actualizarPanelEspecifico();

        if (productoExistente != null) {
            cmbCategoria.setValue(productoExistente.getCategoria());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));

        grid.add(new Label("Tipo:"),      0, 0); grid.add(cmbTipo,      1, 0);
        grid.add(new Label("Nombre:"),    0, 1); grid.add(txtNombre,    1, 1);
        grid.add(new Label("Precio:"),    0, 2); grid.add(txtPrecio,    1, 2);
        grid.add(new Label("Stock:"),     0, 3); grid.add(txtStock,     1, 3);
        grid.add(new Label("Categoría:"), 0, 4); grid.add(cmbCategoria, 1, 4);
        grid.add(panelEspecifico,         0, 5, 2, 1);

        getDialogPane().setContent(grid);
        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        setResultConverter(boton -> {
            if (boton == ButtonType.OK) {
                return construirProducto();
            }
            return null;
        });

        setOnHidden(e -> {
            Producto resultado = getResult();
            if (resultado != null) {
                try {
                    if (productoExistente == null) {
                        gestor.agregar(resultado);
                    } else {
                        gestor.actualizar(resultado);
                    }
                } catch (Exception ex) {
                    new Alert(Alert.AlertType.ERROR, "Error: " + ex.getMessage(), ButtonType.OK).showAndWait();
                }
            }
        });
    }

    private void actualizarCategorias() {
        cmbCategoria.getItems().clear();
        switch (cmbTipo.getValue()) {
            case "ProductoAlimenticio" -> cmbCategoria.getItems().addAll(
                    Categoria.LACTEOS, Categoria.BEBIDAS, Categoria.CARNES,
                    Categoria.VERDURAS, Categoria.PANADERIA);
            case "ProductoElectronico" -> cmbCategoria.getItems().addAll(
                    Categoria.TELEFONIA, Categoria.COMPUTACION, Categoria.ELECTRODOMESTICOS,
                    Categoria.AUDIO_VIDEO, Categoria.GAMING);
            case "ProductoLimpieza" -> cmbCategoria.getItems().addAll(
                    Categoria.DESINFECTANTES, Categoria.DETERGENTES, Categoria.AMBIENTADORES,
                    Categoria.INSECTICIDAS, Categoria.CUIDADO_PERSONAL);
        }
        cmbCategoria.getSelectionModel().selectFirst();
    }

    private void actualizarPanelEspecifico() {
        panelEspecifico.getChildren().clear();
        switch (cmbTipo.getValue()) {
            case "ProductoAlimenticio" -> panelEspecifico.getChildren().addAll(
                    new Label("Fecha de vencimiento:"), dpFechaVencimiento,
                    new Label("Peso (kg):"), txtPeso);
            case "ProductoElectronico" -> panelEspecifico.getChildren().addAll(
                    new Label("Marca:"), txtMarca,
                    new Label("Garantía (meses):"), txtGarantiaMeses);
            case "ProductoLimpieza" -> panelEspecifico.getChildren().addAll(
                    new Label("Tipo de uso:"), txtTipoUso,
                    chkBiodegradable);
        }
        actualizarCategorias();
    }

    private void cargarCamposEspecificos(Producto p) {
        if (p instanceof ProductoAlimenticio pa) {
            dpFechaVencimiento.setValue(pa.getFechaVencimiento());
            txtPeso.setText(String.valueOf(pa.getPeso()));
        } else if (p instanceof ProductoElectronico pe) {
            txtMarca.setText(pe.getMarca());
            txtGarantiaMeses.setText(String.valueOf(pe.getGarantiaMeses()));
        } else if (p instanceof ProductoLimpieza pl) {
            txtTipoUso.setText(pl.getTipoUso());
            chkBiodegradable.setSelected(pl.isEsBiodegradable());
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("El nombre no puede estar vacío.");
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio < 0) {
                mostrarError("El precio no puede ser negativo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un número válido.");
            return false;
        }
        try {
            int stock = Integer.parseInt(txtStock.getText().trim());
            if (stock < 0) {
                mostrarError("El stock no puede ser negativo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarError("El stock debe ser un número entero válido.");
            return false;
        }
        if (cmbCategoria.getValue() == null) {
            mostrarError("Seleccioná una categoría.");
            return false;
        }
        return true;
    }

    private Producto construirProducto() {
        if (!validarCampos()) return null;

        try {
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock     = Integer.parseInt(txtStock.getText().trim());
            Categoria cat = cmbCategoria.getValue();
            int id        = productoExistente != null ? productoExistente.getId() : 0;

            return switch (cmbTipo.getValue()) {
                case "ProductoAlimenticio" -> new ProductoAlimenticio(
                        id, nombre, precio, stock, cat,
                        dpFechaVencimiento.getValue() != null
                                ? dpFechaVencimiento.getValue() : LocalDate.now(),
                        txtPeso.getText().isBlank() ? 0 : Double.parseDouble(txtPeso.getText()));
                case "ProductoElectronico" -> new ProductoElectronico(
                        id, nombre, precio, stock, cat,
                        txtMarca.getText().trim(),
                        txtGarantiaMeses.getText().isBlank() ? 12
                                : Integer.parseInt(txtGarantiaMeses.getText()));
                case "ProductoLimpieza" -> new ProductoLimpieza(
                        id, nombre, precio, stock, cat,
                        txtTipoUso.getText().trim(),
                        chkBiodegradable.isSelected());
                default -> null;
            };
        } catch (NumberFormatException e) {
            mostrarError("Por favor completá todos los campos correctamente.");
            return null;
        }
    }

    private void mostrarError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}