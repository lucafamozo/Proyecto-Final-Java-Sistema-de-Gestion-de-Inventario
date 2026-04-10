/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Luca
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        ProductoController controller = new ProductoController(stage);
        controller.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}